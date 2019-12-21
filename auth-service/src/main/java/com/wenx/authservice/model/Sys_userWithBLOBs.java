package com.wenx.authservice.model;

public class Sys_userWithBLOBs extends Sys_user {
    private byte[] access_token;

    private byte[] refresh_token;

    public byte[] getAccess_token() {
        return access_token;
    }

    public void setAccess_token(byte[] access_token) {
        this.access_token = access_token;
    }

    public byte[] getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(byte[] refresh_token) {
        this.refresh_token = refresh_token;
    }
}