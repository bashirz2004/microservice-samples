package com.zamani;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import java.util.Arrays;


@Configuration
@EnableJms
public class ActiveMQConfig {

    @Value("${active-mq.broker-url}")
    private String brokerUrl;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        //activeMQConnectionFactory.setUserName();
        //activeMQConnectionFactory.setPassword();
        activeMQConnectionFactory.setTrustAllPackages(true);
        //activeMQConnectionFactory.setTrustedPackages(Arrays.asList("com.com.zamani"));
        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(true);
        factory.setClientId("bashir-client");
        //factory.setSubscriptionDurable(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setPubSubDomain(true);  // enable for Pub Sub to topic. Not Required for Queue.
        //jmsTemplate.setDeliveryPersistent(true);
        return jmsTemplate;
    }
}