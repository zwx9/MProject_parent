package com.zwx.base.service;

import com.zwx.base.dao.QuestionDao;
import com.zwx.base.entity.PageResult;
import com.zwx.base.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    //问题列表（分页）
    public Page<Question> findNewQuestionsByLabelId(String LabelId,int start,int pageSize){
        PageRequest pageRequest = PageRequest.of(start-1,pageSize);
        return questionDao.findNewQuestionsByLabelId(LabelId,pageRequest);
    }

    //查询某个标签，根据热门问题排序
    public Page<Question>  findHotQuestionsByLabelId(String labelId ,int start,int pageSize){
        PageRequest pageRequest = PageRequest.of(start - 1, pageSize);
        return questionDao.findHotQuestionsByLabelId(labelId,pageRequest);
    }

    //查询某个标签  未回答的问题列表
    public Page<Question>  findWaitQuestionsByLabelId(String labelId ,int start,int pageSize){
        PageRequest pageRequest = PageRequest.of(start - 1, pageSize);
        return questionDao.findWaitQuestionsByLabelId(labelId,pageRequest);
    }
}
