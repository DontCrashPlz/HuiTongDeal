package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/7.
 */

public class CommodityListEntity {
    //浮动比例
    private float float_rate;

    //产品名称
    private String stock_name;

    //开盘时间
    private String open_time;

    //当前时间
    private String cur_time;

    //remark
    private String remark;

    //关盘时间
    private String close_time;

    //现价
    private float now_price;

    //产品状态产品状态(1开盘 0停盘)
    private int stock_state;

    //当前日期
    private String cur_date;

    //关闭原因
    private String close_reason;

    //id,产品的id
    private int id;

    //数据来源(新浪，百度等)
    private String src_type;

    //产品编码（号）
    private String stock_code;

    public float getFloat_rate() {
        return float_rate;
    }

    public void setFloat_rate(float float_rate) {
        this.float_rate = float_rate;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getCur_time() {
        return cur_time;
    }

    public void setCur_time(String cur_time) {
        this.cur_time = cur_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public float getNow_price() {
        return now_price;
    }

    public void setNow_price(float now_price) {
        this.now_price = now_price;
    }

    public int getStock_state() {
        return stock_state;
    }

    public void setStock_state(int stock_state) {
        this.stock_state = stock_state;
    }

    public String getCur_date() {
        return cur_date;
    }

    public void setCur_date(String cur_date) {
        this.cur_date = cur_date;
    }

    public String getClose_reason() {
        return close_reason;
    }

    public void setClose_reason(String close_reason) {
        this.close_reason = close_reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrc_type() {
        return src_type;
    }

    public void setSrc_type(String src_type) {
        this.src_type = src_type;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    @Override
    public String toString() {
        return "CommodityListEntity{" +
                "float_rate=" + float_rate +
                ", stock_name='" + stock_name + '\'' +
                ", open_time='" + open_time + '\'' +
                ", cur_time='" + cur_time + '\'' +
                ", remark='" + remark + '\'' +
                ", close_time='" + close_time + '\'' +
                ", now_price=" + now_price +
                ", stock_state=" + stock_state +
                ", cur_date='" + cur_date + '\'' +
                ", close_reason='" + close_reason + '\'' +
                ", id=" + id +
                ", src_type='" + src_type + '\'' +
                ", stock_code='" + stock_code + '\'' +
                '}';
    }
}
