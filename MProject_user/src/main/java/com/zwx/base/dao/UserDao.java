package com.zwx.base.dao;

import com.zwx.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    //根据用户名称查找
    public User findByLoginName(String loginName);


    //增加粉丝
    @Modifying
    @Query( "update User u set u.fans =u.fans+1 where u.id=?1")
    public void updateFansIncrease(String friendId);

    //减少粉丝
    @Modifying
    @Query( "update User u set u.fans =u.fans-1 where u.id=?1")
    public void updateFansDecrease(String friendId);
}
