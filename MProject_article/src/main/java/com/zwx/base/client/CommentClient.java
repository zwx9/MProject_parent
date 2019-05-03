package com.zwx.base.client;


import com.zwx.base.entity.Result;
import com.zwx.util.JwtUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient("MProject-comment")
public interface CommentClient {

//
//    @GetMapping("/comment/findAll")
//    public Result findAll();


    @GetMapping("/comment/findById/{id}")
    public Result findById(@PathVariable("id") String id);
//
//    @GetMapping("/comment/findByParentId/{parentId}/{start}/{pageSize}")
//    public Result findByParentId(@PathVariable String parentId,@PathVariable int start,@PathVariable int pageSize);
//
//    //在增加评论时，给当前所在的父节点+1
//    @PostMapping("/comment")//???
//    public Result addComment(@RequestBody CommentClient comment);
//
//    @PutMapping("/comment/{id}")
//    public Result updateComment(@RequestBody Comment comment, @PathVariable String id);
//
//    @DeleteMapping("/comment/{id}")
//    public Result deleteCommentById(@PathVariable String id);

//    @PutMapping("/comment/updateLikes/{userId}/{id}")
//    public Result updateLikes(@RequestBody Comment comment,@PathVariable String userId,@PathVariable String id);




}



