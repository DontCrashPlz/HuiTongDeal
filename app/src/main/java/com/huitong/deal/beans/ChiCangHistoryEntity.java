package com.huitong.deal.beans;

import java.io.Serializable;

/**
 * Created by Zheng on 2018/5/6.
 */

public class ChiCangHistoryEntity implements Serializable {
    //杠杆值
    private int leverage;
    //产品编码
    private String stock_no;
    //购买类型
    private int buy_type;
    //平仓价
    private float end_price;
    //订单金额
    private float order_money;
    //备注
    private String remark;
    //account_status
    private int account_status;
    //止盈率
    private float gain_rate;
    //盈亏
    private float gain;
    //isgain
    private boolean isgain;
    //持仓编号
    private String position_no;
    //deletestatus
    private boolean deletestatus;
    //id
    private int id;
    //point_price
    private float point_price;
    //购买价格
    private float buy_pirce;
    //产品名称
    private String stock_name;
    //购买数量
    private int buy_count;
    //平仓时间
    private String end_time;
    //服务费
    private float service_fee;
    //现价
    private float now_price;
    //服务费率
    private float fee_rate;
    //购买时间
    private String buy_time;
    //user_id
    private int user_id;
    //addtime
    private String addtime;
    //止盈价格
    private float gain_price;
    //止损率
    private float lose_rate;
    //order_state
    private String order_state;
    //close_type
    private int close_type;
    //止损价格
    private float lose_price;

    private int status;

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public String getStock_no() {
        return stock_no;
    }

    public void setStock_no(String stock_no) {
        this.stock_no = stock_no;
    }

    public int getBuy_type() {
        return buy_type;
    }

    public void setBuy_type(int buy_type) {
        this.buy_type = buy_type;
    }

    public float getEnd_price() {
        return end_price;
    }

    public void setEnd_price(float end_price) {
        this.end_price = end_price;
    }

    public float getOrder_money() {
        return order_money;
    }

    public void setOrder_money(float order_money) {
        this.order_money = order_money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getAccount_status() {
        return account_status;
    }

    public void setAccount_status(int account_status) {
        this.account_status = account_status;
    }

    public float getGain_rate() {
        return gain_rate;
    }

    public void setGain_rate(float gain_rate) {
        this.gain_rate = gain_rate;
    }

    public float getGain() {
        return gain;
    }

    public void setGain(float gain) {
        this.gain = gain;
    }

    public boolean isIsgain() {
        return isgain;
    }

    public void setIsgain(boolean isgain) {
        this.isgain = isgain;
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

    public float getPoint_price() {
        return point_price;
    }

    public void setPoint_price(float point_price) {
        this.point_price = point_price;
    }

    public float getBuy_pirce() {
        return buy_pirce;
    }

    public void setBuy_pirce(float buy_pirce) {
        this.buy_pirce = buy_pirce;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public int getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(int buy_count) {
        this.buy_count = buy_count;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public float getService_fee() {
        return service_fee;
    }

    public void setService_fee(float service_fee) {
        this.service_fee = service_fee;
    }

    public float getNow_price() {
        return now_price;
    }

    public void setNow_price(float now_price) {
        this.now_price = now_price;
    }

    public float getFee_rate() {
        return fee_rate;
    }

    public void setFee_rate(float fee_rate) {
        this.fee_rate = fee_rate;
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

    public float getLose_rate() {
        return lose_rate;
    }

    public void setLose_rate(float lose_rate) {
        this.lose_rate = lose_rate;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public int getClose_type() {
        return close_type;
    }

    public void setClose_type(int close_type) {
        this.close_type = close_type;
    }

    public float getLose_price() {
        return lose_price;
    }

    public void setLose_price(float lose_price) {
        this.lose_price = lose_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChiCangHistoryEntity{" +
                "leverage=" + leverage +
                ", stock_no='" + stock_no + '\'' +
                ", buy_type=" + buy_type +
                ", end_price=" + end_price +
                ", order_money=" + order_money +
                ", remark='" + remark + '\'' +
                ", account_status=" + account_status +
                ", gain_rate=" + gain_rate +
                ", gain=" + gain +
                ", isgain=" + isgain +
                ", position_no='" + position_no + '\'' +
                ", deletestatus=" + deletestatus +
                ", id=" + id +
                ", point_price=" + point_price +
                ", buy_pirce=" + buy_pirce +
                ", stock_name='" + stock_name + '\'' +
                ", buy_count=" + buy_count +
                ", end_time='" + end_time + '\'' +
                ", service_fee=" + service_fee +
                ", now_price=" + now_price +
                ", fee_rate=" + fee_rate +
                ", buy_time='" + buy_time + '\'' +
                ", user_id=" + user_id +
                ", addtime='" + addtime + '\'' +
                ", gain_price=" + gain_price +
                ", lose_rate=" + lose_rate +
                ", order_state='" + order_state + '\'' +
                ", close_type=" + close_type +
                ", lose_price=" + lose_price +
                ", status=" + status +
                '}';
    }
}
