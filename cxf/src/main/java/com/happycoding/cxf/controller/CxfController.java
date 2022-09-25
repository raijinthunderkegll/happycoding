package com.happycoding.cxf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CxfController {

    @RequestMapping("hello")
    public String test(){
        return "hello cxf ~~";
    }
}
