package com.huitong.deal.beans_store;

/**
 * Created by Zheng on 2018/6/6.
 */

public class ProductParamEntity {
//    "key":"名称",
//    "value":"黛乐·公爵干红葡萄酒"
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ProductParamEntity{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
