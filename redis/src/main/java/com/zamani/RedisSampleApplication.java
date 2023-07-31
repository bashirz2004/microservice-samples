package com.zamani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RedisSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisSampleApplication.class, args);
    }

}
