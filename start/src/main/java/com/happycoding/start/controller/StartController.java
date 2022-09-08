package com.happycoding.start.controller;

import com.happycoding.start.components.User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("test")
@RestController
public class StartController {

    @RequestMapping("/t1")
    public void testRequestParam(@RequestParam("username") String username, @RequestParam("password")String password){
        System.out.println("username = " + username);
        System.out.println("password = " + password);
    }

    @RequestMapping("t2")
    public String testRequestBody(@RequestBody User user){
        System.out.println(user.toString());
        return user.toString();
    }

    @RequestMapping("getUser")
    public User getUser(){
        return new User("yjl","123");
    }
}
