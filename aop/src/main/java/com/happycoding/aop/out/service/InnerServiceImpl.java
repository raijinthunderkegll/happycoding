package com.happycoding.aop.out.service;

import com.happycoding.aop.model.ApiResponse;
import com.happycoding.aop.service.inner.InnerService;
import org.springframework.stereotype.Service;

@Service
public class InnerServiceImpl implements InnerService {

    @Override
    public ApiResponse api() {
        return new ApiResponse(2,"hahah !!!");
    }
}
