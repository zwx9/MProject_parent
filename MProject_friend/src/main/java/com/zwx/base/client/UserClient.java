package com.zwx.base.client;


import com.zwx.base.entity.Result;
import com.zwx.base.entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value="MProject-user",fallback = UserClientImpl.class)
public interface UserClient {
    //增加
    @PutMapping("user/updateFansIncrease/{friendId}")
    public Result updateFansIncrease(@PathVariable("friendId") String friendId);

    //减少
    @PutMapping("user/updateFansDecrease/{friendId}")
    public Result updateFansDecrease(@PathVariable("friendId") String friendId);
}
