package com.happycoding.aop.controller;

import com.happycoding.aop.model.ApiResponse;
import com.happycoding.aop.service.AopService;
import com.happycoding.aop.service.inner.InnerService;
import com.happycoding.cloud.common.model.Pig;
import com.happycoding.cloud.common.spi.PigService;
import com.happycoding.common.model.SpiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopController {

    @Autowired
    AopService aopService;

    @Autowired
    InnerService innerService;

    @Autowired
    PigService pigService;

    @RequestMapping
    public String test(){
        return aopService.hello("aop");
    }

    @RequestMapping("api")
    public ApiResponse apiResponse(){
        return aopService.apiResponse();
    }

    @RequestMapping("inner")
    public ApiResponse inner(){
        return innerService.api();
    }

    @RequestMapping("pig")
    public SpiResponse<Pig> pig(){
        return pigService.findPig();
    }
}

