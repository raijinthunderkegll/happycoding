package com.happycoding.cloudapi.chicken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.happycoding.cloud"})
@EnableJms
public class ChickenApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChickenApplication.class, args);
    }
}
