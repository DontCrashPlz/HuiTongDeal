package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/6.
 */

public class TiXianHistoryQueryParam {
    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "ChiCangHistoryQueryParam{" +
                " userid=" + userid +
                '}';
    }
}
