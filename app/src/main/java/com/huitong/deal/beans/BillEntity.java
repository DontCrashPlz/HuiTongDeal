package com.huitong.deal.beans;

import java.io.Serializable;

/**
 * Created by Zheng on 2018/5/13.
 */

public class BillEntity implements Serializable {
    //转账会员帐号
    private String from_userid;
    //订单编号
    private String orderno;
    //订单id
    private int orderid;
    //备注描
    private String remark;
    //false收入 true支出
    private boolean type;
    //会员帐号
    private int userid;
    //变动金额
    private float money;
    //addtime
    private String addtime;
    //变动之前余额
    private float before_money;
    //转帐积分
    private int integral;
    //收支种类 1 充值 2提现 3转账  4消费 5分佣
    private int fromtype;
    //deletestatus
    private boolean deletestatus;
    //id
    private int id;

    public String getFrom_userid() {
        return from_userid;
    }

    public void setFrom_userid(String from_userid) {
        this.from_userid = from_userid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public float getBefore_money() {
        return before_money;
    }

    public void setBefore_money(float before_money) {
        this.before_money = before_money;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getFromtype() {
        return fromtype;
    }

    public void setFromtype(int fromtype) {
        this.fromtype = fromtype;
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

    @Override
    public String toString() {
        return "BillEntity{" +
                "from_userid='" + from_userid + '\'' +
                ", orderno='" + orderno + '\'' +
                ", orderid=" + orderid +
                ", remark='" + remark + '\'' +
                ", type=" + type +
                ", userid=" + userid +
                ", money=" + money +
                ", addtime='" + addtime + '\'' +
                ", before_money=" + before_money +
                ", integral=" + integral +
                ", fromtype=" + fromtype +
                ", deletestatus=" + deletestatus +
                ", id=" + id +
                '}';
    }
}
