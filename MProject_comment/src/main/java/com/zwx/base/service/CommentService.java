package com.zwx.base.service;

import com.zwx.base.dao.CommentDao;
import com.zwx.base.entity.Comment;
import com.zwx.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    MongoTemplate mongoTemplate ;

    //查询全部
    public List<Comment> findAll(){
        return commentDao.findAll();
    }

    //根据id查询
    public Comment findById(String id){
        return commentDao.findById(id).get();
    }

    public Page<Comment> findByParentId(String parentId,int start,int pageSize){
        PageRequest pageRequest = PageRequest.of(start - 1, pageSize);
        return commentDao.findByParentId(parentId,pageRequest);
    }

    //添加,根据id寻找parentId，评论自身存储，给parentId加1
    public void addComment(Comment comment){
        comment.set_id(String.valueOf(idWorker.nextId()));
        if (comment.getParentId()!=null && !"".equals(comment.getParentId())){
            Query query = new Query();
            Criteria criteria = Criteria.where("_id").is(comment.getParentId());
            query.addCriteria(criteria);
            Update update = new Update();
            update.inc("parentId",1);
            //条件，操作，数据库名称
            mongoTemplate.updateFirst(query,update,"comment");
        }
        commentDao.save(comment);
    }

    //更新
    public void updateComment(Comment comment){
        commentDao.save(comment);
    }

    //删除
    public void deleteCommentById(String id){
        commentDao.deleteById(id);
    }

    //更新点赞
    public void updateLikes(String id){
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id);//相当于操作条件：where _id = id
        query.addCriteria(criteria);//将条件放到query中
        Update update = new Update();
        update.inc("likes",1);//相当于likes = likes + 1
        mongoTemplate.updateFirst(query,update,"comment");//就是数据库
    }
}
