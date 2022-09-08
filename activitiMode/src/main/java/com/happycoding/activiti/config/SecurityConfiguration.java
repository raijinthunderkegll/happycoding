package com.happycoding.activiti.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfiguration {

    private Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    public UserDetailsService myUserDetailsService() {

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        logger.info("> Registering new user: " + "root" + " with the following Authorities[ 'ACTIVE' , 'ADMIN' ]");

        // 构建 Group 组信息
        List<SimpleGrantedAuthority> groupList = new ArrayList<>();
        // 注意 , 该权限是必须的
        groupList.add(new SimpleGrantedAuthority("ROLE_ACTIVITI_USER"));
        groupList.add(new SimpleGrantedAuthority("ADMIN"));

        // 准备2个用户 : Root , Admin
        inMemoryUserDetailsManager.createUser(new User("root", passwordEncoder().encode("123456"), groupList));
        inMemoryUserDetailsManager.createUser(new User("admin", passwordEncoder().encode("123456"), groupList));

        return inMemoryUserDetailsManager;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

