package com.happycoding.cloudapi.chicken.controller;

import com.happycoding.cloud.common.model.Pig;
import com.happycoding.cloud.common.spi.PigService;
import com.happycoding.common.model.SpiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("chicken")
@RestController
public class ChickenController {

    @Autowired
    PigService pigService;

    @GetMapping("findPig")
    public SpiResponse<Pig> findPig(){
        System.out.println("findPig");
        return pigService.findPig();
//        return SpiResponse.ok();
    }

    @GetMapping("port")
    public SpiResponse<String> port(){
        return pigService.port();
    }
}
