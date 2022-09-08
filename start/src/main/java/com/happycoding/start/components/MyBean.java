package com.happycoding.start.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class MyBean {


    @Autowired
    public MyBean(ApplicationArguments arguments){
        System.out.println(System.getProperty("server.port"));

        Set<String> optionNames = arguments.getOptionNames();
        List<String> nonOptionArgs = arguments.getNonOptionArgs();
        String[] sourceArgs = arguments.getSourceArgs();
        for (String optionName :
                optionNames) {
            System.out.println("optionName: " + optionName);
        }

        for (String nonOptionArg :
                nonOptionArgs) {
            System.out.println("nonOptionArg: " + nonOptionArg);
        }

        for (String sourceArg :
                sourceArgs) {
            System.out.println("sourceArg: " + sourceArg);
        }
    }
}
