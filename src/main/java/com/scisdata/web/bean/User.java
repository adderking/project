package com.scisdata.web.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fangshilei on 18/1/24.
 */
public class User implements Serializable{
    private long id;//用户id
    private String username;//用户名
    private String nickname;//真实名称
    //private String password;
    private String address;//地址
    private String mobile;//手机
    private String email;//邮箱
    private int loginTimes;//登录次数
    private Date reg_time;//注册时间
    private String reg_ip;//注册ip
    private String last_login_time;//最后登录时间
    private Date last_login_ip;//最后登录ip

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(int loginTimes) {
        this.loginTimes = loginTimes;
    }

    public Date getReg_time() {
        return reg_time;
    }

    public void setReg_time(Date reg_time) {
        this.reg_time = reg_time;
    }

    public String getReg_ip() {
        return reg_ip;
    }

    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public Date getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(Date last_login_ip) {
        this.last_login_ip = last_login_ip;
    }
}
