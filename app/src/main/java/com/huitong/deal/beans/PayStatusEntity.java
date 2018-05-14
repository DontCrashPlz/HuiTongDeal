package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/14.
 */

public class PayStatusEntity {
    //订单号
    private String orderno;
    //交易编号
    private String tradeno;
    //支付状态  0:未支付,1:已支付
    private int paystatus;
    //error
    private String error;
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

    public int getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(int paystatus) {
        this.paystatus = paystatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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
        return "PayStatusEntity{" +
                "orderno='" + orderno + '\'' +
                ", tradeno='" + tradeno + '\'' +
                ", paystatus=" + paystatus +
                ", error='" + error + '\'' +
                ", terminaltype='" + terminaltype + '\'' +
                ", ordertype='" + ordertype + '\'' +
                '}';
    }
}
