package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/6.
 */

public class CommitOrderEntity {
    private String positionno;

    public String getPositionno() {
        return positionno;
    }

    public void setPositionno(String positionno) {
        this.positionno = positionno;
    }

    @Override
    public String toString() {
        return "CommitOrderEntity{" +
                "positionno='" + positionno + '\'' +
                '}';
    }
}
