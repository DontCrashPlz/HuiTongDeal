package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/11.
 */

public class CommodityDetailEntity {
    private float close_price;
    private String cur_date;
    private String cur_time;
    private float float_rate;
    private float highest;
    private int id;
    private float lowest;
    private float now_price;
    private float open_price;
    private String state_name;
    private String stock_code;
    private String stock_name;
    private int stock_state;
    private int trade_state;
    private String trade_state_name;

    public float getClose_price() {
        return close_price;
    }

    public void setClose_price(float close_price) {
        this.close_price = close_price;
    }

    public String getCur_date() {
        return cur_date;
    }

    public void setCur_date(String cur_date) {
        this.cur_date = cur_date;
    }

    public String getCur_time() {
        return cur_time;
    }

    public void setCur_time(String cur_time) {
        this.cur_time = cur_time;
    }

    public float getFloat_rate() {
        return float_rate;
    }

    public void setFloat_rate(float float_rate) {
        this.float_rate = float_rate;
    }

    public float getHighest() {
        return highest;
    }

    public void setHighest(float highest) {
        this.highest = highest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLowest() {
        return lowest;
    }

    public void setLowest(float lowest) {
        this.lowest = lowest;
    }

    public float getNow_price() {
        return now_price;
    }

    public void setNow_price(float now_price) {
        this.now_price = now_price;
    }

    public float getOpen_price() {
        return open_price;
    }

    public void setOpen_price(float open_price) {
        this.open_price = open_price;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public int getStock_state() {
        return stock_state;
    }

    public void setStock_state(int stock_state) {
        this.stock_state = stock_state;
    }

    public int getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(int trade_state) {
        this.trade_state = trade_state;
    }

    public String getTrade_state_name() {
        return trade_state_name;
    }

    public void setTrade_state_name(String trade_state_name) {
        this.trade_state_name = trade_state_name;
    }

    @Override
    public String toString() {
        return "CommodityDetailEntity{" +
                "close_price=" + close_price +
                ", cur_date='" + cur_date + '\'' +
                ", cur_time='" + cur_time + '\'' +
                ", float_rate=" + float_rate +
                ", highest=" + highest +
                ", id=" + id +
                ", lowest=" + lowest +
                ", now_price=" + now_price +
                ", open_price=" + open_price +
                ", state_name='" + state_name + '\'' +
                ", stock_code='" + stock_code + '\'' +
                ", stock_name='" + stock_name + '\'' +
                ", stock_state=" + stock_state +
                ", trade_state=" + trade_state +
                ", trade_state_name='" + trade_state_name + '\'' +
                '}';
    }
}
