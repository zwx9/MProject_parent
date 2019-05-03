package com.zwx.base.service;

import com.zwx.base.dao.ArticleESDao;
import com.zwx.base.entity.Article;
import com.zwx.base.entity.PageResult;
import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ArticleESService {

    @Autowired
    private ArticleESDao articleESDao;

    //存入搜索引擎
    public void saveArticle(Article article){
        articleESDao.save(article);
    }

    public Page<Article> findByTitleOrDescriptionLike(String keywords,int start,int pageSize ){
        PageRequest request = PageRequest.of(start, pageSize);
        return  articleESDao.findByTitleOrDescriptionLike(keywords,keywords,request);
    }




}
