package com.zwx.base.controller;

import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.entity.User;
import com.zwx.base.service.UserService;
import com.zwx.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    /**以下是通过短信发送验证码注册登录*/
    @PostMapping("sendSms/{phone}")
    public Result sendSms(@PathVariable String phone){
        userService.sendSms(phone);
        return new Result(true, StatusCode.OK,"短信发送成功") ;
    }

    //注册新用户
    @PostMapping("register/{smsCode}")
    public Result register(@RequestBody User user, @PathVariable String smsCode){
        userService.addUser(user,smsCode);
        return new Result(true, StatusCode.OK,"注册成功") ;
    }

    /**以下是在注册时进行密码加密并登录*/
    //注册
    @PostMapping("register")
    public Result register(@RequestBody User user){
        userService.addUser(user);
        return new Result(true, StatusCode.OK,"注册成功") ;
    }

    //登录
    @GetMapping("login")
    public Result login(@RequestBody Map<String,String> loginMap){
        User user = userService.findUserByLoginNameAndPassword(loginMap.get("loginName"),loginMap.get("password"));
        if (user == null){
            return new Result(false, StatusCode.LOGINERROR,"用户名或密码错误！") ;
        }
        //token
        String token = jwtUtil.createJWT(user.getId(), user.getLoginName(), "user");
        Map map = new HashMap();
        map.put("token",token);
        map.put("user",user);
        return new Result(true, StatusCode.OK,"登录成功",map) ;
    }

    //对方增加粉丝
    @PutMapping(value = "updateFans/{increase}/{friendId}")
    public Result updateFans(@PathVariable("increase") int increase,@PathVariable("friendId") String friendId){
        userService.updateFriendFans(increase,friendId);
        return new Result(true, StatusCode.OK, "对方粉丝已更新！");
    }

    //删除：先判断token(Authorization)
    @DeleteMapping("{id}")
    public Result delete(@PathVariable String id) {
        Claims claims = (Claims)request.getAttribute("admin_claims");
        if(claims == null ){
            return new Result(false, StatusCode.ACCESSERROR, "权限不足！");
        }
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功！");
    }



}
