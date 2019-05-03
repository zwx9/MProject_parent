package com.zwx.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

//开启网关
@EnableZuulProxy
@SpringBootApplication
public class ManagerApplication {
    public static void main(String[] args)  {

        SpringApplication.run( ManagerApplication.class);
    }
}
