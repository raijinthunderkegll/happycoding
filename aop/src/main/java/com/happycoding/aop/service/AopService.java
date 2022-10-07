package com.happycoding.aop.service;

import com.happycoding.aop.annotation.AopAnno;
import com.happycoding.aop.model.ApiResponse;

//注解加载接口上，@Pointcut的 @within无法匹配，@within、@target、@annotation都只能匹配目标实现类
@AopAnno
public interface AopService {

    String hello(String name);

    ApiResponse apiResponse();
}
