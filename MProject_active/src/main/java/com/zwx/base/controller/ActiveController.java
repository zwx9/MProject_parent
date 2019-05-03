package com.zwx.base.controller;

import com.zwx.base.entity.Active;
import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.service.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("active")
public class ActiveController {
    @Autowired
    private ActiveService activeService ;

    //根据活动项目的id查找
    @GetMapping("findActiveById/{id}")
    public Result findActiveById(@PathVariable String id){
        return new Result(true, StatusCode.OK,"查询成功！",activeService.findActiveById(id));
    }

    //根据活动地点查询同城活动


    @PostMapping()
    public Result addActive(@RequestBody Active active){
        activeService.addOrUpdateActive(active);
        return new Result(true, StatusCode.OK,"增加成功！");
    }

    @PutMapping()
    public Result updateActive(@RequestBody Active active){
        activeService.addOrUpdateActive(active);
        return new Result(true, StatusCode.OK,"修改成功！");
    }

    @DeleteMapping("{id}")
//    @DeleteMapping()
    public Result deleteActiveById(@PathVariable String id){
        activeService.deleteActiveById(id);
        return new Result(true, StatusCode.OK,"删除成功！");
    }
}
