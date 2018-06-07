package com.huitong.deal.beans_store;

/**
 * Created by Zheng on 2018/6/7.
 */

public class AreaEntity {
//    "text":"北京市",
//    "value":"4521984"
    private String text;
    private String value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AreaEntity{" +
                "text='" + text + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
