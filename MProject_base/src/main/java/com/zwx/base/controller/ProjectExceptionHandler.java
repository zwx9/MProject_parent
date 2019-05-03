package com.zwx.base.controller;

import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
public class ProjectExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
        public Result projectException(Exception e){
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,e.getMessage());
        }

}
