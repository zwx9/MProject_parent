package com.zwx.base.service;

import com.zwx.base.dao.UserDao;
import com.zwx.base.entity.User;
import com.zwx.util.IdWorker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
//@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BCryptPasswordEncoder encoder ;


    /**
     * 以下是通过短信发送验证码注册登录
     * */

    //产生验证码并发送
    @Transactional
    public void sendSms(String phone){
        //1.产生6位验证码
        String smsCode = ((int)(Math.random()*900000+100000))+"";
        //2.存入redis缓存，有效时间为60秒
        redisTemplate.opsForValue().set("smsCode_"+phone,smsCode,60, TimeUnit.SECONDS);
        System.out.println(phone+"-"+smsCode);
        //3.发送验证码到MQ
        Map<String, String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("smsCode",smsCode);
        //map是向sms队列中添加的一个新元素
        rabbitTemplate.convertAndSend("sms",map);
    }

    //验证用户输入的验证码
    @Transactional
    public void addUser(User user,String smsCode){
        //从redis中获取验证码
        String redisCode = (String) redisTemplate.opsForValue().get("smsCode_" + user.getPhone());
        //判断验证码是否正确
        if (!redisCode.equals(smsCode)){
            throw new RuntimeException("验证码错误！");
        }
            //进行用户的初始化
            user.setId( idWorker.nextId()+"");
            user.setFans(0);
            user.setRegisterTime( new Date());
            user.setUpdateTime(new Date());
            user.setLastLoginTime(new Date() );
            userDao.save(user) ;
    }


    /**
     * 以下是在普通用户注册时进行密码加密并登录
     * */

    //注册
    @Transactional
    public void addUser(User user){
        user.setId(idWorker.nextId()+"");
        //给密码进行加密
        String encodePwd = encoder.encode(user.getPassword());
        //将加密后的密码赋予新密码存入用户
        user.setPassword(encodePwd);
        user.setRegisterTime( new Date());
        user.setUpdateTime(new Date());
        user.setLastLoginTime(new Date() );
        //存入用户
        userDao.save(user);
    }

    //登录
    @Transactional
    public User findUserByLoginNameAndPassword(String loginName,String password){
        User user = userDao.findByLoginName(loginName);
        if (user != null && encoder.matches(password,user.getPassword())){
            return user;
        }
        return null;
    }

    //对方增加粉丝
    @Transactional
    public void updateFansIncrease(String friendId){
        userDao.updateFansIncrease(friendId);
    }

    //对方减少粉丝
    @Transactional
    public void updateFansDecrease(String friendId){
        userDao.updateFansDecrease(friendId);
    }

    //根据Id删除
    @Transactional
    public void deleteById(String id){
        userDao.deleteById(id);
    }

}
