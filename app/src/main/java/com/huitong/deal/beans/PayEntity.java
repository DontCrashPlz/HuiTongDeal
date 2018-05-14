package com.huitong.deal.beans;

import java.io.Serializable;

/**
 * Created by Zheng on 2018/5/14.
 */

public class PayEntity implements Serializable {
    //订单编号
    private String orderno;
    //支付交易编号
    private String tradeno;
    //支付金额
    private String total_fee;
    //tradestatus
    private String tradestatus;
    //支付二维码地址
    private String payqrcode;
    //支付类型
    private String paytype;
    //错误代码
    private String errorcode;
    //错误提示消息
    private String errormsg;
    //终端类型
    private String terminaltype;
    //订单类型
    private String ordertype;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getTradeno() {
        return tradeno;
    }

    public void setTradeno(String tradeno) {
        this.tradeno = tradeno;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTradestatus() {
        return tradestatus;
    }

    public void setTradestatus(String tradestatus) {
        this.tradestatus = tradestatus;
    }

    public String getPayqrcode() {
        return payqrcode;
    }

    public void setPayqrcode(String payqrcode) {
        this.payqrcode = payqrcode;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getTerminaltype() {
        return terminaltype;
    }

    public void setTerminaltype(String terminaltype) {
        this.terminaltype = terminaltype;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    @Override
    public String toString() {
        return "PayEntity{" +
                "orderno='" + orderno + '\'' +
                ", tradeno='" + tradeno + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", tradestatus='" + tradestatus + '\'' +
                ", payqrcode='" + payqrcode + '\'' +
                ", paytype='" + paytype + '\'' +
                ", errorcode='" + errorcode + '\'' +
                ", errormsg='" + errormsg + '\'' +
                ", terminaltype='" + terminaltype + '\'' +
                ", ordertype='" + ordertype + '\'' +
                '}';
    }
}
