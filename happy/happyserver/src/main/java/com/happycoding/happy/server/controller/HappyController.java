package com.happycoding.happy.server.controller;

import com.happycoding.common.model.SpiResponse;
import com.happycoding.happy.server.model.LuckDrawRequest;
import com.happycoding.happy.server.service.HappyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("happy")
public class HappyController {

    @Autowired
    HappyService happyService;

    @PostMapping("luckDraw")
    public SpiResponse<String> luckDraw(@RequestBody LuckDrawRequest request){
        return SpiResponse.ok(happyService.luckDraw("yang"));
    }
}
