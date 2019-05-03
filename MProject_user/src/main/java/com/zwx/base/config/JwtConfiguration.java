package com.zwx.base.config;

import com.zwx.base.interceptor.JwtInterceptor;
import com.zwx.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//配置拦截器
@Configuration
public class JwtConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry register){
        //login登录之后才会生成token，因此不拦截login操作
        register.addInterceptor(jwtInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/**/login");
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }




}
