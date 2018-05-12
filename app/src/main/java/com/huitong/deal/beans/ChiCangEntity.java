package com.huitong.deal.beans;

import java.io.Serializable;

/**
 * Created by Zheng on 2018/5/6.
 */

public class ChiCangEntity implements Serializable {
    //杠杆
    private int leverage;
    //产品名称
    private String stockname;
    //产品编码
    private String stock_no;
    //购买价格
    private float buy_pirce;
    //交易方式(0跌（回购） 1涨(认购))
    private int buy_type;
    //现价
    private float curpoint;
    //购买数量
    private int buy_count;
    //服务费
    private float service_fee;
    //订单金额
    private float order_money;
    //建仓价
    private float now_price;
    //止盈率
    private float gain_rate;
    //服务费率
    private float fee_rate;
    //盈亏(浮动盈亏)
    private float gain;
    //购买时间
    private String buy_time;
    //暂无注释
    private int user_id;
    //addtime
    private String addtime;
    //止盈价格
    private float gain_price;
    //产品id
    private int stockid;
    //持仓编号
    private String position_no;
    //deletestatus
    private boolean deletestatus;
    //持仓订单的id
    private int id;
    //止损率
    private float lose_rate;
    //point_price
    private float point_price;
    //止损价格
    private float lose_price;

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public String getStock_no() {
        return stock_no;
    }

    public void setStock_no(String stock_no) {
        this.stock_no = stock_no;
    }

    public float getBuy_pirce() {
        return buy_pirce;
    }

    public void setBuy_pirce(float buy_pirce) {
        this.buy_pirce = buy_pirce;
    }

    public int getBuy_type() {
        return buy_type;
    }

    public void setBuy_type(int buy_type) {
        this.buy_type = buy_type;
    }

    public float getCurpoint() {
        return curpoint;
    }

    public void setCurpoint(float curpoint) {
        this.curpoint = curpoint;
    }

    public int getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(int buy_count) {
        this.buy_count = buy_count;
    }

    public float getService_fee() {
        return service_fee;
    }

    public void setService_fee(float service_fee) {
        this.service_fee = service_fee;
    }

    public float getOrder_money() {
        return order_money;
    }

    public void setOrder_money(float order_money) {
        this.order_money = order_money;
    }

    public float getNow_price() {
        return now_price;
    }

    public void setNow_price(float now_price) {
        this.now_price = now_price;
    }

    public float getGain_rate() {
        return gain_rate;
    }

    public void setGain_rate(float gain_rate) {
        this.gain_rate = gain_rate;
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

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public float getGain_price() {
        return gain_price;
    }

    public void setGain_price(float gain_price) {
        this.gain_price = gain_price;
    }

    public int getStockid() {
        return stockid;
    }

    public void setStockid(int stockid) {
        this.stockid = stockid;
    }

    public String getPosition_no() {
        return position_no;
    }

    public void setPosition_no(String position_no) {
        this.position_no = position_no;
    }

    public boolean isDeletestatus() {
        return deletestatus;
    }

    public void setDeletestatus(boolean deletestatus) {
        this.deletestatus = deletestatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLose_rate() {
        return lose_rate;
    }

    public void setLose_rate(float lose_rate) {
        this.lose_rate = lose_rate;
    }

    public float getPoint_price() {
        return point_price;
    }

    public void setPoint_price(float point_price) {
        this.point_price = point_price;
    }

    public float getLose_price() {
        return lose_price;
    }

    public void setLose_price(float lose_price) {
        this.lose_price = lose_price;
    }

    @Override
    public String toString() {
        return "ChiCangEntity{" +
                "leverage=" + leverage +
                ", stockname='" + stockname + '\'' +
                ", stock_no='" + stock_no + '\'' +
                ", buy_pirce=" + buy_pirce +
                ", buy_type=" + buy_type +
                ", curpoint=" + curpoint +
                ", buy_count=" + buy_count +
                ", service_fee=" + service_fee +
                ", order_money=" + order_money +
                ", now_price=" + now_price +
                ", gain_rate=" + gain_rate +
                ", fee_rate=" + fee_rate +
                ", gain=" + gain +
                ", buy_time='" + buy_time + '\'' +
                ", user_id=" + user_id +
                ", addtime='" + addtime + '\'' +
                ", gain_price=" + gain_price +
                ", stockid=" + stockid +
                ", position_no='" + position_no + '\'' +
                ", deletestatus=" + deletestatus +
                ", id=" + id +
                ", lose_rate=" + lose_rate +
                ", point_price=" + point_price +
                ", lose_price=" + lose_price +
                '}';
    }
}
