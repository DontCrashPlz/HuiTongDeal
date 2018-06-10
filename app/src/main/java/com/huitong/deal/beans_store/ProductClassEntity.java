package com.huitong.deal.beans_store;

/**
 * Created by Zheng on 2018/6/9.
 */

public class ProductClassEntity {
    private String classname;
    private int id;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductClassEntity{" +
                "classname='" + classname + '\'' +
                ", id=" + id +
                '}';
    }
}
