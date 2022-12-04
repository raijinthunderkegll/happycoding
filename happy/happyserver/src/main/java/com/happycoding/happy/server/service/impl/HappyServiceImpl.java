package com.happycoding.happy.server.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.happycoding.happy.server.service.HappyService;
import org.springframework.stereotype.Service;

@Service
public class HappyServiceImpl implements HappyService {
    @Override
    public String luckDraw(String userId) {
        return RandomUtil.randomNumbers(10);
    }
}
