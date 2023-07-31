package com.zamani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@EnableEurekaClient
public class PublisherSubscriberApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublisherSubscriberApplication.class, args);
    }

    @Bean
    //@LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(60))
                .build();
    }
}
