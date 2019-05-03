package com.zwx.base.controller;

import com.zwx.base.entity.Comment;
import com.zwx.base.entity.PageResult;
import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("findAll")
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询全部成功",commentService.findAll());
    }

    @GetMapping("findById/{id}")
    public Result findById(@PathVariable("id") String id){
        return new Result(true, StatusCode.OK,"根据id查询成功",commentService.findById(id));
    }

    @GetMapping("findByParentId/{parentId}/{start}/{pageSize}")
    public Result findByParentId(@PathVariable String parentId,@PathVariable int start,@PathVariable int pageSize){
        Page<Comment> pageInfo = commentService.findByParentId(parentId, start-1, pageSize);
        PageResult<Comment> pageResult = new PageResult<>(pageInfo.getTotalElements(), pageInfo.getContent());
        return new Result(true,StatusCode.OK,"ParentId查询成功",pageResult) ;
    }

    //在增加评论时，给当前所在的父节点+1
    @PostMapping
    public Result addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
        return new Result(true,StatusCode.OK,"增加成功") ;
    }

    @PutMapping("{id}")
    public Result updateComment(@RequestBody Comment comment,@PathVariable String id){
        comment.set_id(id);
        commentService.updateComment(comment);
        return new Result(true,StatusCode.OK,"修改成功") ;
    }

    @DeleteMapping("{id}")
    public Result deleteCommentById(@PathVariable String id){
        commentService.deleteCommentById(id);
        return new Result(true,StatusCode.OK,"删除成功") ;
    }

    @PutMapping("updateLikes/{userId}/{id}")
    public Result updateLikes(@RequestBody Comment comment,@PathVariable String userId,@PathVariable String id){
        comment.setUserId(userId);
        if (redisTemplate.opsForValue().get("likes_"+userId+"_"+id) != null){
            return new Result(false,StatusCode.ERROR,"不能重复点赞");
        }
        commentService.updateLikes(id);
        redisTemplate.opsForValue().set("likes_"+userId+"_"+id,"1");
        return new Result(true,StatusCode.OK,"点赞成功") ;
    }



}
