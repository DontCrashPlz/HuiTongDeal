package com.huitong.deal.beans;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/5/12.
 */

public class TimeLineDataEntity {
    private String name;
    private ArrayList<String> value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public void setValue(ArrayList<String> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TimeLineDataEntity{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
