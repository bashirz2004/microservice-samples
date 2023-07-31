package com.zamani;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zamani.messagepackage.MyHttpRequestMessage;
import com.zamani.messagepackage.MyHttpResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JmsPublisherAndSubscriber implements MessageListener {
    private final RestTemplate restTemplate;
    private final JmsTemplate jmsTemplate;

    @Value("${active-mq.topic-in}")
    private String topicIn;

    @Value("${active-mq.topic-out}")
    private String topicOut;

    public JmsPublisherAndSubscriber(RestTemplate restTemplate, JmsTemplate jmsTemplate) {
        this.restTemplate = restTemplate;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    @JmsListener(destination = "${active-mq.topic-in}", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            MyHttpRequestMessage myHttpRequestMessage = (MyHttpRequestMessage) objectMessage.getObject();
            log.info("Received request Message: {} from topic: {}", myHttpRequestMessage.toString(), topicIn);

            //make resttemplate call to vidzy based on object myHttpRequestMessage
            String url = myHttpRequestMessage.getText();
            // if request method is GET. for POST,PUT, ... should write code
            ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET,null, Object.class);
            //make MyHttpResponseMessage from resttemplate response
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponseEntity = objectMapper.writeValueAsString(responseEntity);

            //publish message to out-queue
            MyHttpResponseMessage myHttpResponseMessage = MyHttpResponseMessage.builder()
                    .request(myHttpRequestMessage)
                    .text("whole response data in json format : " + jsonResponseEntity)
                    .build();
            this.sendMessage(myHttpResponseMessage);
        } catch (Exception e) {
            //here should publish error message to out-queue
            log.error("Received Exception : " + e);
        }

    }

    public void sendMessage(MyHttpResponseMessage message) {
        try {
            log.info("Attempting Send message: {} to Topic: {} ", message.toString(), topicOut);
            jmsTemplate.convertAndSend(topicOut, message);
            log.info("Sent message: {} to Topic: {} ", message.toString(), topicOut);
        } catch (Exception e) {
            log.error("Received Exception during send Message: ", e);
        }
    }
}
