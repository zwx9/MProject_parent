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
@FeignClient(value = "MProject-user")
public interface UserClient {
    @PutMapping(value = "user/updateFans/{increase}/{friendId}")
    public Result updateFans(@PathVariable("increase") int increase,@PathVariable("friendId") String friendId);
}
