package com.huitong.deal.beans_store;

/**
 * Created by Zheng on 2018/6/9.
 */

public class RecentInviteEntity {
    //"user_no":null
    private String user_no;

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    @Override
    public String toString() {
        return "RecentInviteEntity{" +
                "user_no='" + user_no + '\'' +
                '}';
    }
}
