package com.zwx.base.dao;

import com.zwx.base.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CommentDao extends MongoRepository<Comment,String> {
    public Page<Comment> findByParentId(String parentId, Pageable pageable);
}
