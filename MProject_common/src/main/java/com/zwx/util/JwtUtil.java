package com.zwx.util;

import io.jsonwebtoken.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
//@ConfigurationProperties("jwt.config")
public class JwtUtil {
    private String key ; //佐料盐
    private long expire ; //过期时间
    public JwtUtil(){}

    public JwtUtil(String key, long expire) {
        this.key = key;
        this.expire = expire;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    /**
     * 1. new Date((expire+date.getTime()))含义:
     *      将自己设置的过期时间expire加上获取的当前时间date.getTime()，就是new Date()即将要过期的时间
     * 2. claim("roles",roles)
     *      自定义属性
     * */
    public String createJWT(String id,String subject,String roles){
        JwtBuilder builder = null;
        Date date = new Date();
        //链式编程 -> 函数式编程
        builder = Jwts.builder().setId(id).setSubject(subject)
                  .setIssuedAt(date).signWith(SignatureAlgorithm.HS256,key.getBytes())
                  .setExpiration(new Date((expire+date.getTime()))).claim("roles",roles);
        return builder.compact();
    }

    public Claims parseJwt(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e){
            System.out.println("超时");
        }catch(Exception e){
            e.printStackTrace();
        }
        return claims;
    }
}
