package com.huitong.deal.beans;

import java.io.Serializable;

/**
 * Created by Zheng on 2018/6/10.
 */

public class ChiCangEntity2 implements Serializable {
    private String addtime;
    private float after_balance;
    private float before_balance;
    private int buy_count;
    private float buy_pirce;
    private String buy_time;
    private int buy_type;
    private float cur_point;
    private float curpoint;
    private boolean deletestatus;
    private float end_price;
    private float fee_rate;
    private float gain;
    private float gain_price;
    private float gain_rate;
    private int id;
    private int leverage;
    private float lose_price;
    private float lose_rate;
    private float now_price;
    private float order_money;
    private float point_price;
    private String position_no;
    private float service_fee;
    private String stock_no;
    private int stockid;
    private String stockname;
    private int user_id;

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public float getAfter_balance() {
        return after_balance;
    }

    public void setAfter_balance(float after_balance) {
        this.after_balance = after_balance;
    }

    public float getBefore_balance() {
        return before_balance;
    }

    public void setBefore_balance(float before_balance) {
        this.before_balance = before_balance;
    }

    public int getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(int buy_count) {
        this.buy_count = buy_count;
    }

    public float getBuy_pirce() {
        return buy_pirce;
    }

    public void setBuy_pirce(float buy_pirce) {
        this.buy_pirce = buy_pirce;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public int getBuy_type() {
        return buy_type;
    }

    public void setBuy_type(int buy_type) {
        this.buy_type = buy_type;
    }

    public float getCur_point() {
        return cur_point;
    }

    public void setCur_point(float cur_point) {
        this.cur_point = cur_point;
    }

    public float getCurpoint() {
        return curpoint;
    }

    public void setCurpoint(float curpoint) {
        this.curpoint = curpoint;
    }

    public boolean isDeletestatus() {
        return deletestatus;
    }

    public void setDeletestatus(boolean deletestatus) {
        this.deletestatus = deletestatus;
    }

    public float getEnd_price() {
        return end_price;
    }

    public void setEnd_price(float end_price) {
        this.end_price = end_price;
    }

    public float getFee_rate() {
        return fee_rate;
    }

    public void setFee_rate(float fee_rate) {
        this.fee_rate = fee_rate;
    }

    public float getGain() {
        return gain;
    }

    public void setGain(float gain) {
        this.gain = gain;
    }

    public float getGain_price() {
        return gain_price;
    }

    public void setGain_price(float gain_price) {
        this.gain_price = gain_price;
    }

    public float getGain_rate() {
        return gain_rate;
    }

    public void setGain_rate(float gain_rate) {
        this.gain_rate = gain_rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public float getLose_price() {
        return lose_price;
    }

    public void setLose_price(float lose_price) {
        this.lose_price = lose_price;
    }

    public float getLose_rate() {
        return lose_rate;
    }

    public void setLose_rate(float lose_rate) {
        this.lose_rate = lose_rate;
    }

    public float getNow_price() {
        return now_price;
    }

    public void setNow_price(float now_price) {
        this.now_price = now_price;
    }

    public float getOrder_money() {
        return order_money;
    }

    public void setOrder_money(float order_money) {
        this.order_money = order_money;
    }

    public float getPoint_price() {
        return point_price;
    }

    public void setPoint_price(float point_price) {
        this.point_price = point_price;
    }

    public String getPosition_no() {
        return position_no;
    }

    public void setPosition_no(String position_no) {
        this.position_no = position_no;
    }

    public float getService_fee() {
        return service_fee;
    }

    public void setService_fee(float service_fee) {
        this.service_fee = service_fee;
    }

    public String getStock_no() {
        return stock_no;
    }

    public void setStock_no(String stock_no) {
        this.stock_no = stock_no;
    }

    public int getStockid() {
        return stockid;
    }

    public void setStockid(int stockid) {
        this.stockid = stockid;
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ChiCangEntity2{" +
                "addtime='" + addtime + '\'' +
                ", after_balance=" + after_balance +
                ", before_balance=" + before_balance +
                ", buy_count=" + buy_count +
                ", buy_pirce=" + buy_pirce +
                ", buy_time='" + buy_time + '\'' +
                ", buy_type=" + buy_type +
                ", cur_point=" + cur_point +
                ", curpoint=" + curpoint +
                ", deletestatus=" + deletestatus +
                ", end_price=" + end_price +
                ", fee_rate=" + fee_rate +
                ", gain=" + gain +
                ", gain_price=" + gain_price +
                ", gain_rate=" + gain_rate +
                ", id=" + id +
                ", leverage=" + leverage +
                ", lose_price=" + lose_price +
                ", lose_rate=" + lose_rate +
                ", now_price=" + now_price +
                ", order_money=" + order_money +
                ", point_price=" + point_price +
                ", position_no='" + position_no + '\'' +
                ", service_fee=" + service_fee +
                ", stock_no='" + stock_no + '\'' +
                ", stockid=" + stockid +
                ", stockname='" + stockname + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
