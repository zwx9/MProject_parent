package com.zwx.base.controller;

import com.zwx.base.entity.Label;
import com.zwx.base.entity.PageResult;
import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 * @ResponseBody : 对象 -> json
 * @RequestBody : json -> 对象
 *                json -> map
 * */
@RefreshScope
@RestController
@RequestMapping("label")
public class LabelController {
    @Autowired
    private LabelService labelService;
    @Value("${zwx.name}")
    private String myname;


    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        System.out.println("*****"+myname+"*****");
        return new Result(true, StatusCode.OK,"查询成功", labelService.findAll() );
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public Result findLabelById(@PathVariable("id") String id){
        return new Result(true,StatusCode.OK,"根据Id查询成功~",labelService.findLabelById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result saveLabel(@RequestBody Label label){
        labelService.saveLabel(label);
        return new Result(true,StatusCode.OK,"增加成功",labelService.findAll());
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public Result updateLabel(@RequestBody Label label, @PathVariable("id") String id){
        label.setId(id);
        labelService.updateLabel(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public Result deleteLabel(@PathVariable("id") String id){
        labelService.deleteLabelById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "findLabels",method = RequestMethod.GET)
    public Result findLabels(@RequestBody Map searchMap){
        return new Result(true, StatusCode.OK,"模糊查询成功", labelService.findLabels(searchMap) );
    }

    //  关键字 / 起始行数（起始行数的下一行开始显示分页） / 每一页的数据数量
    @RequestMapping(value = "findLabels/{start}/{pageSize}",method = RequestMethod.GET)
    public Result findLabels(@RequestBody Map searchMap, @PathVariable int start, @PathVariable int pageSize){
        Page labelsPage = labelService.findLabels(searchMap, start, pageSize);
        long totalSize = labelsPage.getTotalElements();//总数据量
        List<Label> labels = labelsPage.getContent();//每页的数据
        PageResult pageResult = new PageResult(totalSize, labels);
        return new Result(true,StatusCode.OK,"分页查询成功",pageResult);

    }



}
