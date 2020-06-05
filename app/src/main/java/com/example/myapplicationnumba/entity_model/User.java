package com.example.myapplicationnumba.entity_model;

import java.util.Date;

/**
 * 用户：
 * 包含整个系统中的所有用户，用户信息
 *
 * @Author:zuohang
 * @date:2020/5/13 0013 11:05
 */
public class User {
    //主键
    private Integer userId;
    //用户名称
    private String name;
    //密码
    private String password;
    //性别
    private String sex;
    //邮箱
    private String email;
    //电话号码
    private String phoneNumber;
    //状态
    private Integer status;
    //用户注册事件
    private Date createTime;
    //头像
    private String headShot;
    //用户上次登录时间
    private Date lastLoginTime;
    //用户上次更新时间
    private Date lastUpdateTime;

//    public User(String phoneNumber, String password) {
//        this.phoneNumber = phoneNumber;
//        this.name = phoneNumber;
//        this.password = password;
//        this.sex = "男";
//    }

    public User() {
    }

    public User(Integer userId, String name, String password, String sex, String email, String phoneNumber, Integer status, Date createTime, String headShot, Date lastLoginTime, Date lastUpdateTime) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.createTime = createTime;
        this.headShot = headShot;
        this.lastLoginTime = lastLoginTime;
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getHeadShot() {
        return headShot;
    }

    public void setHeadShot(String headShot) {
        this.headShot = headShot;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", headShot='" + headShot + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
