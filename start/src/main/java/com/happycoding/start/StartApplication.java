package com.happycoding.start;

import com.happycoding.start.components.PropBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
@RestController
public class StartApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
//        SpringApplication app = new SpringApplication(StartApplication.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);


//        new SpringApplicationBuilder();
        System.out.println(new Date().getTime());
    }

    // 不能给static属性赋值
    @Value("${my.number}")
    Integer number;

    @Value("${my.secret}")
    String secret;

    @Value("${home}")
    String home;
    @Value("${path}")
    String path;

    @Autowired
    PropBean propBean;

    @RequestMapping("/")
    public String index() {
        System.out.println("number: " + number);
        System.out.println("secret: " + secret);
        System.out.println("propBean.getNumber()" + propBean.getNumber());
        System.out.println("propBean.getInner()" + propBean.getInner().getName());
        System.out.println("sys-env-home" + System.getenv().get("HOME"));
        System.out.println("home" + home);
        System.out.println("path" + path);
        System.out.println("map" + propBean.getMap());
        System.out.println("list" + propBean.getList().get(0));
        System.out.println("duration" + propBean.getSessionTimeout().getSeconds());
        return "hello world";
    }
}
