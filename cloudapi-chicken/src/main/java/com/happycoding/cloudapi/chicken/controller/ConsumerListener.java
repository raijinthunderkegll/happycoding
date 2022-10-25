package com.happycoding.cloudapi.chicken.controller;

import com.happycoding.cloud.common.model.Pig;
import com.happycoding.cloud.common.spi.PigService;
import com.happycoding.common.model.SpiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

//@RestController
public class ConsumerListener {

    @Autowired
    PigService pigService;

    @JmsListener(destination = "${spring.activemq.queue-name: chicken}", containerFactory = "queueListener")
    public void read(String message) {
        System.out.println("read message: " + message);
        SpiResponse<Pig> pig = pigService.findPig();
        System.out.println(pig.getData());
        SpiResponse<Pig> pigByName = pigService.findPigByName(message);
        System.out.println(pigByName.getData());
    }
}
