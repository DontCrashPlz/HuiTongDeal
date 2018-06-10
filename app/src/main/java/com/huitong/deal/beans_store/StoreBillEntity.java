package com.huitong.deal.beans_store;

import java.io.Serializable;

/**
 * Created by Zheng on 2018/6/11.
 */

public class StoreBillEntity implements Serializable {
    private String account_type;
    private String addtime;
    private float after_integral;
    private float after_money;
    private String before_integral;
    private float before_money;
    private String from_type_name;
    private int fromtype;
    private float integral;
    private String mobile;
    private float money;
    private String orderno;
    private String remark;
    private boolean type;

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public float getAfter_integral() {
        return after_integral;
    }

    public void setAfter_integral(float after_integral) {
        this.after_integral = after_integral;
    }

    public float getAfter_money() {
        return after_money;
    }

    public void setAfter_money(float after_money) {
        this.after_money = after_money;
    }

    public String getBefore_integral() {
        return before_integral;
    }

    public void setBefore_integral(String before_integral) {
        this.before_integral = before_integral;
    }

    public float getBefore_money() {
        return before_money;
    }

    public void setBefore_money(float before_money) {
        this.before_money = before_money;
    }

    public String getFrom_type_name() {
        return from_type_name;
    }

    public void setFrom_type_name(String from_type_name) {
        this.from_type_name = from_type_name;
    }

    public int getFromtype() {
        return fromtype;
    }

    public void setFromtype(int fromtype) {
        this.fromtype = fromtype;
    }

    public float getIntegral() {
        return integral;
    }

    public void setIntegral(float integral) {
        this.integral = integral;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StoreBillEntity{" +
                "account_type='" + account_type + '\'' +
                ", addtime='" + addtime + '\'' +
                ", after_integral=" + after_integral +
                ", after_money=" + after_money +
                ", before_integral='" + before_integral + '\'' +
                ", before_money=" + before_money +
                ", from_type_name='" + from_type_name + '\'' +
                ", fromtype=" + fromtype +
                ", integral=" + integral +
                ", mobile='" + mobile + '\'' +
                ", money=" + money +
                ", orderno='" + orderno + '\'' +
                ", remark='" + remark + '\'' +
                ", type=" + type +
                '}';
    }
}
