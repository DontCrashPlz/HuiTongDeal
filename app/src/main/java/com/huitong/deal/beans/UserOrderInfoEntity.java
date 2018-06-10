package com.huitong.deal.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zheng on 2018/6/10.
 */

public class UserOrderInfoEntity {
    @SerializedName("10")
    private int ordernum_waitpay;//待付款订单
    @SerializedName("20")
    private int ordernum_waitSend;//待发货订单
    @SerializedName("30")
    private int ordernum_waitReceive;//待收货订单
    @SerializedName("50")
    private int ordernum_received;//已收货订单

    public int getOrdernum_waitpay() {
        return ordernum_waitpay;
    }

    public void setOrdernum_waitpay(int ordernum_waitpay) {
        this.ordernum_waitpay = ordernum_waitpay;
    }

    public int getOrdernum_waitSend() {
        return ordernum_waitSend;
    }

    public void setOrdernum_waitSend(int ordernum_waitSend) {
        this.ordernum_waitSend = ordernum_waitSend;
    }

    public int getOrdernum_waitReceive() {
        return ordernum_waitReceive;
    }

    public void setOrdernum_waitReceive(int ordernum_waitReceive) {
        this.ordernum_waitReceive = ordernum_waitReceive;
    }

    public int getOrdernum_received() {
        return ordernum_received;
    }

    public void setOrdernum_received(int ordernum_received) {
        this.ordernum_received = ordernum_received;
    }

    @Override
    public String toString() {
        return "UserOrderInfoEntity{" +
                "ordernum_waitpay=" + ordernum_waitpay +
                ", ordernum_waitSend=" + ordernum_waitSend +
                ", ordernum_waitReceive=" + ordernum_waitReceive +
                ", ordernum_received=" + ordernum_received +
                '}';
    }
}
