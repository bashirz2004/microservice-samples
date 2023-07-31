package com.zamani;

import com.zamani.messagepackage.MyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProduceMessageController {
    private final JmsTemplate jmsTemplate;

    @Value("${active-mq.queue-in-out}")
    private String queueInOut;

    public ProduceMessageController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping(value="/api/produce")
    public MyMessage sendMessage(@RequestBody MyMessage myMessage){
        try {
            log.info("Attempting Send message to QUEUE: " + queueInOut);
            jmsTemplate.convertAndSend(queueInOut, myMessage);
            log.info("Sent message to QUEUE: " + queueInOut);
        } catch (Exception e) {
            log.error("Received Exception during send Message: ", e);
        }
        return myMessage;
    }

}