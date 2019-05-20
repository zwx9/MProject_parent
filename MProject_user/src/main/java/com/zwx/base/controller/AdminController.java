package com.zwx.base.controller;

import com.zwx.base.entity.Admin;
import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.service.AdminService;
import com.zwx.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AdminService adminService;
//    @Autowired
//    private JwtUtil jwtUtil;


    /**以下是在注册时进行密码加密并登录*/
    //注册
    @PostMapping("register")
    public Result register(@RequestBody Admin admin){
        adminService.addAdmin(admin);
        return new Result(true, StatusCode.OK,"注册成功") ;
    }

    //登录
    @GetMapping("login")
    public Result login(@RequestBody Map<String,String> loginMap){
        Admin admin = adminService.findAdminByLoginNameAndPassword(loginMap.get("loginName"),loginMap.get("password"));
        if (admin == null){
            return new Result(false, StatusCode.LOGINERROR,"用户名或密码错误！") ;
        }
        //token
//        String token = jwtUtil.createJWT(admin.getId(), admin.getLoginName(), "admin");
        Map map = new HashMap();
//        map.put("token",token);
        map.put("admin",admin);
        return new Result(true, StatusCode.OK,"登录成功",map) ;
    }

    //删除：先判断token(Authrorization)
    @DeleteMapping("{id}")
    public Result delete(@PathVariable String id) {
        Claims claims = (Claims)request.getAttribute("admin_claims");
        if(claims == null){
            return new Result(false, StatusCode.ACCESSERROR, "权限不足！");
        }
        adminService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功！");
    }

}
