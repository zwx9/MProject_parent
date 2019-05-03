package com.zwx.base.service;

import com.zwx.base.dao.AdminDao;
import com.zwx.base.entity.Admin;
import com.zwx.base.entity.User;
import com.zwx.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BCryptPasswordEncoder encoder ;

    /**以下是在注册时进行密码加密并登录*/
    //注册
    public void addAdmin(Admin admin){
        admin.setId(idWorker.nextId()+"");
        //给密码进行加密
        String encodePwd = encoder.encode(admin.getPassword());
        //将加密后的密码赋予新密码存入用户
        admin.setPassword(encodePwd);
        //存入用户
        adminDao.save(admin);
    }

    //登录
    public Admin findAdminByLoginNameAndPassword(String loginName,String password){
        Admin admin = adminDao.findByLoginName(loginName);
        if (admin != null && encoder.matches(password,admin.getPassword())){
            return admin;
        }
        return null;
    }

    //删除
    public void deleteById(String id){
        adminDao.deleteById(id);
    }
}
