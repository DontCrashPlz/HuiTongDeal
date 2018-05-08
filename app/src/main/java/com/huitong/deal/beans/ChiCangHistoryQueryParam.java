package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/6.
 */

public class ChiCangHistoryQueryParam {
//    private String user_id;
    private int userid;

//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "ChiCangHistoryQueryParam{" +
//                "user_id='" + user_id + '\'' +
                ", userid=" + userid +
                '}';
    }
}
