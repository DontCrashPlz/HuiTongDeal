package com.huitong.deal.beans;

import java.io.Serializable;

/**
 * Created by Zheng on 2018/5/14.
 */

public class ChongZhiHistoryEntity implements Serializable {
    //客户经理名称
    private String five_level_name;
    //remittance_user
    private String remittance_user;
    //remittance_time
    private String remittance_time;
    //before_balance
    private float before_balance;
    //remark
    private String remark;
    //pc_no
    private String pc_no;
    //暂无注释
    private int three_level_id;
    //部门主管名称
    private String four_level_name;
    //加盟公司名称
    private String two_level_name;
    //支付方式
    private String pay_type;
    //deletestatus
    private boolean deletestatus;
    //id
    private int id;
    //汇款帐号
    private String remittance_account;
    //代理公司名称
    private String three_level_name;
    //运营中心名称
    private String one_level_name;
    //充值后余额
    private float after_balance;
    //真实姓名
    private String truename;
    //充值金额
    private float amount;
    //four_level_id
    private int four_level_id;
    //服务费
    private float service_fee;
    //汇款备注
    private String remittance_info;
    //five_level_id
    private int five_level_id;
    //支付时间
    private String pay_time;
    //交易编号
    private String pay_trade_no;
    //one_level_id
    private int one_level_id;
    //two_level_id
    private int two_level_id;
    //user_id
    private int user_id;
    //addtime
    private String addtime;
    //admin_id
    private int admin_id;
    //充值方式(0 线下 1线上)
    private int online;
    //充值状态(0 未支付 1已支付 2已取消)
    private int status;

    public String getFive_level_name() {
        return five_level_name;
    }

    public void setFive_level_name(String five_level_name) {
        this.five_level_name = five_level_name;
    }

    public String getRemittance_user() {
        return remittance_user;
    }

    public void setRemittance_user(String remittance_user) {
        this.remittance_user = remittance_user;
    }

    public String getRemittance_time() {
        return remittance_time;
    }

    public void setRemittance_time(String remittance_time) {
        this.remittance_time = remittance_time;
    }

    public float getBefore_balance() {
        return before_balance;
    }

    public void setBefore_balance(float before_balance) {
        this.before_balance = before_balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPc_no() {
        return pc_no;
    }

    public void setPc_no(String pc_no) {
        this.pc_no = pc_no;
    }

    public int getThree_level_id() {
        return three_level_id;
    }

    public void setThree_level_id(int three_level_id) {
        this.three_level_id = three_level_id;
    }

    public String getFour_level_name() {
        return four_level_name;
    }

    public void setFour_level_name(String four_level_name) {
        this.four_level_name = four_level_name;
    }

    public String getTwo_level_name() {
        return two_level_name;
    }

    public void setTwo_level_name(String two_level_name) {
        this.two_level_name = two_level_name;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
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

    public String getRemittance_account() {
        return remittance_account;
    }

    public void setRemittance_account(String remittance_account) {
        this.remittance_account = remittance_account;
    }

    public String getThree_level_name() {
        return three_level_name;
    }

    public void setThree_level_name(String three_level_name) {
        this.three_level_name = three_level_name;
    }

    public String getOne_level_name() {
        return one_level_name;
    }

    public void setOne_level_name(String one_level_name) {
        this.one_level_name = one_level_name;
    }

    public float getAfter_balance() {
        return after_balance;
    }

    public void setAfter_balance(float after_balance) {
        this.after_balance = after_balance;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getFour_level_id() {
        return four_level_id;
    }

    public void setFour_level_id(int four_level_id) {
        this.four_level_id = four_level_id;
    }

    public float getService_fee() {
        return service_fee;
    }

    public void setService_fee(float service_fee) {
        this.service_fee = service_fee;
    }

    public String getRemittance_info() {
        return remittance_info;
    }

    public void setRemittance_info(String remittance_info) {
        this.remittance_info = remittance_info;
    }

    public int getFive_level_id() {
        return five_level_id;
    }

    public void setFive_level_id(int five_level_id) {
        this.five_level_id = five_level_id;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPay_trade_no() {
        return pay_trade_no;
    }

    public void setPay_trade_no(String pay_trade_no) {
        this.pay_trade_no = pay_trade_no;
    }

    public int getOne_level_id() {
        return one_level_id;
    }

    public void setOne_level_id(int one_level_id) {
        this.one_level_id = one_level_id;
    }

    public int getTwo_level_id() {
        return two_level_id;
    }

    public void setTwo_level_id(int two_level_id) {
        this.two_level_id = two_level_id;
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

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChongZhiHistoryEntity{" +
                "five_level_name='" + five_level_name + '\'' +
                ", remittance_user='" + remittance_user + '\'' +
                ", remittance_time='" + remittance_time + '\'' +
                ", before_balance=" + before_balance +
                ", remark='" + remark + '\'' +
                ", pc_no='" + pc_no + '\'' +
                ", three_level_id=" + three_level_id +
                ", four_level_name='" + four_level_name + '\'' +
                ", two_level_name='" + two_level_name + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", deletestatus=" + deletestatus +
                ", id=" + id +
                ", remittance_account='" + remittance_account + '\'' +
                ", three_level_name='" + three_level_name + '\'' +
                ", one_level_name='" + one_level_name + '\'' +
                ", after_balance=" + after_balance +
                ", truename='" + truename + '\'' +
                ", amount=" + amount +
                ", four_level_id=" + four_level_id +
                ", service_fee=" + service_fee +
                ", remittance_info='" + remittance_info + '\'' +
                ", five_level_id=" + five_level_id +
                ", pay_time='" + pay_time + '\'' +
                ", pay_trade_no='" + pay_trade_no + '\'' +
                ", one_level_id=" + one_level_id +
                ", two_level_id=" + two_level_id +
                ", user_id=" + user_id +
                ", addtime='" + addtime + '\'' +
                ", admin_id=" + admin_id +
                ", online=" + online +
                ", status=" + status +
                '}';
    }
}
