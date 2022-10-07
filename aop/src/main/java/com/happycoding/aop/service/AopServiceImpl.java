package com.happycoding.aop.service;

import com.happycoding.aop.model.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class AopServiceImpl implements AopService{

    @Override
    public String hello(String name){
        return "hello " + (name == null ? "world" : name);
    }

    @Override
    public ApiResponse apiResponse() {
        return new ApiResponse(1,"hello world lp");
    }
}
