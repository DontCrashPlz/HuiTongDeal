package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/6.
 */

public class LoginEntity {
    private String apptoken;

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }

    @Override
    public String toString() {
        return "LoginEntity{" +
                "apptoken='" + apptoken + '\'' +
                '}';
    }
}
