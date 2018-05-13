package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/13.
 */

public class ChongZhiEntity {
    private String pc_no;
    private int id;

    public String getPc_no() {
        return pc_no;
    }

    public void setPc_no(String pc_no) {
        this.pc_no = pc_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ChongZhiEntity{" +
                "pc_no='" + pc_no + '\'' +
                ", id=" + id +
                '}';
    }
}
