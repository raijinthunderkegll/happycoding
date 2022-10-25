package com.happycoding.cloudapi.pig.spi.impl;

import com.happycoding.cloud.common.model.Pig;
import com.happycoding.cloud.common.spi.PigService;
import com.happycoding.common.model.SpiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PigServiceImpl implements PigService {

    @Value("${server.port}")
    String serverport;
    @Override
    public SpiResponse<Pig> findPig() {
        System.out.println(serverport);
        return SpiResponse.ok(new Pig("peggy"));
    }

    @Override
    public SpiResponse<Pig> findPigByName(String name) {
        return SpiResponse.ok(new Pig("lala"));
    }

    @Override
    public SpiResponse<String> port() {
        System.out.println(serverport);
        return SpiResponse.ok(serverport);
    }


}
