package com.zwx.base.controller;

import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.entity.User;
import com.zwx.base.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;
//    @Autowired
//    private JwtUtil jwtUtil;

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
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/index");
        userService.addUser(user);
        return new Result(true, StatusCode.OK,"注册成功",mav) ;
    }

    //登录
    @GetMapping("login1")
    public Result login(@RequestBody Map<String,String> loginMap){

        User user = userService.findUserByLoginNameAndPassword(loginMap.get("loginName"),loginMap.get("password"));
        if (user == null){
            return new Result(false, StatusCode.LOGINERROR,"用户名或密码错误！") ;
        }
        //token
//        String token = jwtUtil.createJWT(user.getId(), user.getLoginName(), "user");
        Map map = new HashMap();
//        map.put("token",token);
        map.put("user",user);
        return new Result(true, StatusCode.OK,"登录成功",map) ;
    }


    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView){
        modelAndView.setViewName("login");
        return modelAndView;
    }

   /* //登录
    @PostMapping("/login")
    public String login(@Valid Map<String,String> loginMap, ModelAndView modelAndView,

                              HttpServletRequest request){

//        User user = userService.findUserByLoginNameAndPassword(loginMap.get("loginName"),loginMap.get("password"));

//        modelAndView.addAttribute("user", loginMap.get("loginName"));
//        modelAndView.addAttribute("user", loginMap.get("password"));

        String loginName = loginMap.get("loginName");
        String password = loginMap.get("password");


        modelAndView.addObject("loginName",loginName);
        modelAndView.setViewName("index");
        return "hello";

    }*/


    //对方增加粉丝
    @PutMapping("updateFansIncrease/{friendId}")
    public Result updateFansIncrease(@PathVariable("friendId") String friendId) {
        userService.updateFansIncrease(friendId);
        System.out.println("===============================");
        return new Result(true, StatusCode.OK, "粉丝减一！");
    }

    //对方减少粉丝
    @PutMapping("updateFansDecrease/{friendId}")
    public Result updateFansDecrease(@PathVariable("friendId") String friendId) {
        userService.updateFansDecrease(friendId);
        System.out.println("===============================");
        return new Result(true, StatusCode.OK, "粉丝加一！");
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
