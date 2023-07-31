package com.zamani;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zamani.messagepackage.MyMessage;
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

@Component
@Slf4j
public class JmsConsumer implements MessageListener {

    @Value("${active-mq.queue-in-out}")
    private String queueInOut;


    @Override
    @JmsListener(destination = "${active-mq.queue-in-out}", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            MyMessage myMessage = (MyMessage) objectMessage.getObject();
            log.info("Received request Message: {} from queueInOut: {}", myMessage.toString(), queueInOut);
        } catch (Exception e) {
            log.error("Received Exception : " + e);
        }
    }


}
