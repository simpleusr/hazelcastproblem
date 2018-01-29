package com.mycompany.member.config;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {
    
    @Bean(name = "closeLatch")
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }
    
    
   

}
