package com.zwx.base.interceptor;

import com.zwx.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    //Authorization -> 授权，认可，批准
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        //前缀 + token
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")){
            //获取token
            String token = authorization.substring(7);
            //解析token
            Claims claims = jwtUtil.parseJwt(token);
            //判断claims是否为空，再判断是否是管理员或普通用户
            if (claims != null){
                //判断是否为管理员
                if ("admin".equals(claims.get("roles"))){
                    request.setAttribute("admin_claims",claims);
                }else if("user".equals(claims.get("roles"))){

                    request.setAttribute("user_claims",claims);
                }else {
                    throw new RuntimeException("身份有误！");
                }
            }
            return true;
        }
        return true;//拦截器放行
    }
}
