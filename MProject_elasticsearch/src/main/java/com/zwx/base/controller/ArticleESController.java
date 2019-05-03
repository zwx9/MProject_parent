package com.zwx.base.controller;

import com.zwx.base.entity.Article;
import com.zwx.base.entity.PageResult;
import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.service.ArticleESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("article")
public class ArticleESController {

    @Autowired
    private ArticleESService articleESService;

    @PostMapping
    public Result saveArticle(@RequestBody Article article){
        articleESService.saveArticle(article);
        return new Result(true, StatusCode.OK,"存入引擎成功") ;
    }

    @GetMapping("{keywords}/{start}/{pageSize}")
    public Result findByTitleOrDescriptionLike(@PathVariable String keywords, @PathVariable int start, @PathVariable int pageSize){
        Page<Article> articlePage = articleESService.findByTitleOrDescriptionLike(keywords, start - 1, pageSize);
        return new Result(true,StatusCode.OK,"搜索成功",new PageResult<Article>(articlePage.getTotalElements(),articlePage.getContent() ));
    }

}
