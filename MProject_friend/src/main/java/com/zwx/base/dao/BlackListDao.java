package com.zwx.base.dao;

import com.zwx.base.entity.BlackList;
import com.zwx.base.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BlackListDao extends JpaRepository<BlackList,String>, JpaSpecificationExecutor<BlackList> {

    //判断此人是否存在黑名单中(0:不在黑名单       1:已在黑名单中)
    @Query(nativeQuery = true,value = "select count(*) from tb_blacklist where user_id = ? and friend_id = ?")
    public int selectFriendBlack(String userId,String friendId);

}
