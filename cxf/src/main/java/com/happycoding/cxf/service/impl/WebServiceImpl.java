package com.happycoding.cxf.service.impl;

import com.happycoding.cxf.service.WebService;
import org.springframework.stereotype.Service;

@Service("webServiceImpl")
public class WebServiceImpl implements WebService {
    @Override
    public void test() {
        System.out.println("test cxf");
    }
}
