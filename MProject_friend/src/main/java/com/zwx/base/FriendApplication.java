package com.zwx.base;

import com.zwx.base.client.UserClient;
import com.zwx.util.IdWorker;
import com.zwx.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class FriendApplication {
    public static void main(String[] args){
        SpringApplication.run(FriendApplication.class);
    }



//    @Bean //IdWorker放入ioc容器 （1. @Bean +返回值  2.三层注解 ）
//    public IdWorker idWorker(){
//        return new IdWorker(1,1);
//    }
}
