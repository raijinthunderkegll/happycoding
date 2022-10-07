package com.happycoding.aop.aspect;

import com.happycoding.common.model.SpiResponse;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopAspect {

    @Pointcut("@within(com.happycoding.aop.annotation.AopAnno)")
    public void point(){}

    @Pointcut("execution(public com.happycoding.aop.model.ApiResponse com.happycoding.aop.service..*(..))")
    public void pointEx(){}

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void pointService(){}

    @Pointcut("execution(public * com.happycoding.cloud..*(..))")
    public void pointFeign(){}

//    @Around("pointEx()")
//    public Object around(){
//        System.out.println("around~~~~~~~`");
//        return null;
//    }

    @AfterReturning(value="pointEx()", returning = "result")
    public Object afterReturn(Object result){
        System.out.println("hello after returning");
        return result;
    }

    @AfterReturning(value="pointFeign()", returning = "result")
    public Object afterReturnFeign(Object result){
        System.out.println("feign client call returning");
        if(result instanceof SpiResponse){
            System.out.println("spi code = " + ((SpiResponse)result).getCode());
        }
        return result;
    }
}
