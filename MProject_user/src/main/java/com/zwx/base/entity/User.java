package com.zwx.base.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="tb_user")
public class User implements Serializable {
    @Id
    private String id;
    private String username ;
    @NotEmpty(message="用户名不能为空")
    private String loginName;
    @NotEmpty(message="密码不能为空")
    @Length(min=6, message="密码长度不能少于六位")
    private String password;
    private String phone ;
    private int fans ;//粉丝数
    private Date registerTime ;
    private Date updateTime ;
    private Date lastLoginTime ;

    public User() {
    }

    public User(String username, String loginName, String password, String phone, int fans, Date registerTime, Date updateTime, Date lastLoginTime) {
        this.username = username;
        this.loginName = loginName;
        this.password = password;
        this.phone = phone;
        this.fans = fans;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
        this.lastLoginTime = lastLoginTime;
    }

    public User(String id, String username, String loginName, String password, String phone, int fans, Date registerTime, Date updateTime, Date lastLoginTime) {
        this.id = id;
        this.username = username;
        this.loginName = loginName;
        this.password = password;
        this.phone = phone;
        this.fans = fans;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
        this.lastLoginTime = lastLoginTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}