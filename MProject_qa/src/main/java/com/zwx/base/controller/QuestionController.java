package com.zwx.base.controller;

import com.zwx.base.entity.Question;
import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    //问题列表（分页）
    @RequestMapping(value = "findNewQuestionsByLabelId/{labelid}/{start}/{pagesize}",method = RequestMethod.GET)
    public Result findNewQuestionsByLabelId(@PathVariable String labelid,@PathVariable int start,@PathVariable int pagesize){
        Page<Question> questions = questionService.findNewQuestionsByLabelId(labelid, start, pagesize);
        return new Result(true, StatusCode.OK,"question查询成功",questions);
    }

    //查询某个标签，根据热门问题排序
    @GetMapping(value="hotquesionlist/{labelid}/{start}/{pagesize}")
    public Result findHotQuestionsByLabelId(@PathVariable String labelid,@PathVariable int start,@PathVariable int pagesize){
        Page<Question> quesionsPage = questionService.findHotQuestionsByLabelId(labelid, start, pagesize);
        return new Result(true, StatusCode.OK,"查询成功",quesionsPage  );
    }

    //查询某个标签  未回答的问题列表
    @GetMapping(value="waitquesionlist/{labelid}/{start}/{pagesize}")
    public Result findWaitQuestionsByLabelId(@PathVariable String labelid,@PathVariable int start,@PathVariable int pagesize){
        Page<Question> quesionsPage = questionService.findWaitQuestionsByLabelId(labelid, start, pagesize);
        return new Result(true, StatusCode.OK,"查询成功",quesionsPage  );
    }






}
