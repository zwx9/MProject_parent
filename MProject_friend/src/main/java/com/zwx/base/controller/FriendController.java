package com.zwx.base.controller;

import com.zwx.base.client.UserClient;
import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.service.FriendService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 0:不能添加自己
     * 1:好友已存在，不能重复添加
     * 2:好友添加成功
     * */
    @PostMapping(value ="addfriend/{friendId}")
    public Result addFriend(@PathVariable("friendId") String friendId){
        //身份验证
        Claims claims = (Claims) request.getAttribute("user_claims");
        System.out.println(claims);
        if(claims == null){
            return new Result(false, StatusCode.ACCESSERROR,"操作权限不足，请登录!");
        }
        //进行添加操作
        int result = friendService.addFriend(claims.getId(), friendId);
        //判断状态
        if(result == 0){
            return new Result(false, StatusCode.ACCESSERROR,"不能添加自己!");
        }else if(result == 1){
            return new Result(false, StatusCode.ACCESSERROR,"好友已存在，不能重复添加!");
        }else {
            return new Result(true, StatusCode.OK,"好友添加成功!");
        }
    }


    /**
     * 设置关注
     * 0:添加关注成功！(成功后粉丝数量加一)
     *               (功能：在登录者在关注自己的朋友同时，朋友的粉丝数量加一)
     * 1:取消关注！
     * */

    @PutMapping(value ="setIsStar/{friendId}")
    public Result setIsStar(@PathVariable("friendId") String friendId){
        //身份验证
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims == null){
            return new Result(false, StatusCode.ACCESSERROR,"操作权限不足，请登录!");
        }

        //关注
        int result = friendService.setIsStar(claims.getId(),friendId);

        //判断状态
        if(result == 1){
            return new Result(false, StatusCode.ACCESSERROR,"取消关注!");
        }else if (result == 0){
            return new Result(true, StatusCode.OK,"添加关注成功!");
        }
        //return = -1
        return new Result(false, StatusCode.ERROR,"你和对方还不是好友，请先添加再关注!");
    }

    /**
     * 设置黑名单
     * */
    @PostMapping(value ="setFriendBlack/{friendId}")
    public Result setFriendBlack(@PathVariable("friendId") String friendId){
        //身份验证
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims == null){
            return new Result(false, StatusCode.ACCESSERROR,"操作权限不足，请登录!");
        }
        friendService.setFriendBlack(claims.getId(), friendId);
        return new Result(true,StatusCode.OK,"已将此人拉进黑名单!");
    }

    /**
     * 移除黑名单
     * */
    @DeleteMapping(value ="deleteFromBlack/{friendId}")
    public Result deleteFromBlack(@PathVariable("friendId") String friendId){
        //身份验证
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims == null){
            return new Result(false, StatusCode.ACCESSERROR,"操作权限不足，请登录!");
        }
        friendService.deleteFromBlack(claims.getId(), friendId);
        return new Result(true,StatusCode.OK,"已将此人移除黑名单!");
    }

    /**
     * 删除好友并拉进黑名单
     *      0:不在黑名单       1:已在黑名单中
     * */
    @DeleteMapping(value ="deleteAndBlack/{friendId}")
    public Result deleteAndBlack(@PathVariable("friendId") String friendId){
        //身份验证
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims == null){
            return new Result(false, StatusCode.ACCESSERROR,"操作权限不足，请登录!");
        }

        //进行添加操作
        int result = friendService.deleteAndBlack(claims.getId(),friendId);
        //判断状态
        if(result == 1){
            return new Result(false, StatusCode.ERROR,"此人已在黑名单中!");
        }else {
            return new Result(true, StatusCode.OK,"已将此人删除并拉黑!");
        }
    }



}
