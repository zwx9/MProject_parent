package com.zwx.base.client;

import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class UserClientImpl implements UserClient {

    public Result updateFansIncrease(String fiendId) {
        System.out.println("熔断器启动了增加..........");
        return new Result(false, StatusCode.ERROR, "远程调用出现，启动熔断保护....");
    }

    public Result updateFansDecrease(String friendId) {
        System.out.println("熔断器启动了减少..........");
        return new Result(false, StatusCode.ERROR, "远程调用出现，启动熔断保护....");
    }
}
