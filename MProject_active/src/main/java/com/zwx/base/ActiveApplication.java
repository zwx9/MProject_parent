package com.zwx.base;

import com.zwx.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

//开启缓存
@EnableCaching
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ActiveApplication {
    public static void main(String[] args) {
        SpringApplication.run( ActiveApplication.class);
    }

    @Bean //IdWorker放入ioc容器 （1. @Bean +返回值  2.三层注解 ）
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}

