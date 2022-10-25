package com.happycoding.cloud.common.spi;

import com.happycoding.cloud.common.model.Pig;
import com.happycoding.common.model.SpiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feignclient.pig.name:pig}")
public interface PigService {

    @RequestMapping("findPig")
    public SpiResponse<Pig> findPig();

    @RequestMapping("findPigByName")
    public SpiResponse<Pig> findPigByName(@RequestParam("name") String name);

    @RequestMapping("port")
    public SpiResponse<String> port();

}
