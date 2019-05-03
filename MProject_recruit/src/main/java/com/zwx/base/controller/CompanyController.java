package com.zwx.base.controller;

import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import com.zwx.base.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @RequestMapping(value = "popularList/{isPopular}" ,method = RequestMethod.GET)
    public Result popularList(@PathVariable String isPopular){
        return new Result(true, StatusCode.OK,"是否热门查询成功",companyService.popularList(isPopular));
    }
}
