package com.zwx.base.dao;

import com.zwx.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    //根据用户名称查找
    public User findByLoginName(String loginName);


    //增加粉丝
    @Modifying
    @Query(nativeQuery = true,value = "update tb_user set fans = fans + ? where id = ?")
    public void updateFriendFans(int increase,String friendId);

}
