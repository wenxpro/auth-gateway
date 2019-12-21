package com.wenx.authservice.model;

import java.util.Date;

public class Sys_user {
    private String id;

    private String username;

    private String password;

    private String nickname;

    private String mobile;

    private String email;

    private Date create_date;

    private String create_userid;

    private Date del_date;

    private String del_uerid;

    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getCreate_userid() {
        return create_userid;
    }

    public void setCreate_userid(String create_userid) {
        this.create_userid = create_userid == null ? null : create_userid.trim();
    }

    public Date getDel_date() {
        return del_date;
    }

    public void setDel_date(Date del_date) {
        this.del_date = del_date;
    }

    public String getDel_uerid() {
        return del_uerid;
    }

    public void setDel_uerid(String del_uerid) {
        this.del_uerid = del_uerid == null ? null : del_uerid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}