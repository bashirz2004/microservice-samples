package com.zamani.producer;

import com.zamani.messagepackage.MyHttpRequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PublishMessageController {

    private final JmsProducer jmsProducer;

    public PublishMessageController(JmsProducer jmsProducer) {
        this.jmsProducer = jmsProducer;
    }

    @PostMapping(value="/api/produce")
    public MyHttpRequestMessage sendMessage(@RequestBody MyHttpRequestMessage myMessage){
        jmsProducer.sendMessage(myMessage);
        return myMessage;
    }

}