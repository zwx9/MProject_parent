package com.zwx.base.dao;

import com.zwx.base.entity.Admin;
import com.zwx.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminDao extends JpaRepository<Admin,String>, JpaSpecificationExecutor<Admin> {
    //根据用户名称查找
    public Admin findByLoginName(String loginName);

}
