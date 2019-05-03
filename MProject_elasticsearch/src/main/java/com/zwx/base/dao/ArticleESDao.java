package com.zwx.base.dao;

import com.zwx.base.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleESDao extends ElasticsearchRepository<Article,String> {
    public Page<Article> findByTitleOrDescriptionLike(String title, String description, Pageable pageable);
}
