package com.zwx.base.controller;

import com.zwx.base.client.CommentClient;
import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private CommentClient commentClient;
    @Autowired
    private ArticleService articleService ;
//    @Autowired
//    private RedisTemplate redisTemplate;


    @GetMapping(value = "findById/{id}")
    public Result findById(@PathVariable("id") String id){
        System.out.println("-------------Article-------------");
        System.out.println(commentClient);
        return commentClient.findById(id);
    }

    //get  put  delete  post
    @PutMapping("reviewArticle/{id}")
    public Result reviewArticle(@PathVariable String id){
        articleService.reviewArticle(id);
        return new Result(true, StatusCode.OK,"审核成功！");
    }
    @PutMapping("updateLikes/{id}")
    public Result updateLikes(@PathVariable String id){
        articleService.updateLikes(id);
        return new Result(true, StatusCode.OK,"点赞成功！");
    }
    //查询  redis
    @RequestMapping(value = "findByArticleId/{id}",method = RequestMethod.GET)
    public Result findByArticleId(@PathVariable String id){
        return new Result(true, StatusCode.OK,"查询成功！",articleService.findByArticleId(id));
    }


    /*@Bean
    public CommentClient commentClient(){
        return new CommentClient();
    }*/
}

