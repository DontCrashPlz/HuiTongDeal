package com.huitong.deal.beans;

import java.io.Serializable;

/**
 * Created by Zheng on 2018/5/6.
 */

public class TiXianHistoryEntity implements Serializable {
    private float after_balance;
    private String cach_account;
    private String cach_bark_branch;
    private String cach_user_name;
    private String cach_bark;
    private String refuse_reason;
    private float service_fee;
    private float before_balance;
    private String remark;
    private String cach_no;
    private String pay_time;
    private String pay_trade_no;
    private String cach_time;
    private float cach_amount;
    private int user_id;
    private String addtime;
    private int admin_id;
    private String pay_type;
    private boolean deletestatus;
    private int id;
    private int status;

    public float getAfter_balance() {
        return after_balance;
    }

    public void setAfter_balance(float after_balance) {
        this.after_balance = after_balance;
    }

    public String getCach_account() {
        return cach_account;
    }

    public void setCach_account(String cach_account) {
        this.cach_account = cach_account;
    }

    public String getCach_bark_branch() {
        return cach_bark_branch;
    }

    public void setCach_bark_branch(String cach_bark_branch) {
        this.cach_bark_branch = cach_bark_branch;
    }

    public String getCach_user_name() {
        return cach_user_name;
    }

    public void setCach_user_name(String cach_user_name) {
        this.cach_user_name = cach_user_name;
    }

    public String getCach_bark() {
        return cach_bark;
    }

    public void setCach_bark(String cach_bark) {
        this.cach_bark = cach_bark;
    }

    public String getRefuse_reason() {
        return refuse_reason;
    }

    public void setRefuse_reason(String refuse_reason) {
        this.refuse_reason = refuse_reason;
    }

    public float getService_fee() {
        return service_fee;
    }

    public void setService_fee(float service_fee) {
        this.service_fee = service_fee;
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

    public String getCach_no() {
        return cach_no;
    }

    public void setCach_no(String cach_no) {
        this.cach_no = cach_no;
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

    public String getCach_time() {
        return cach_time;
    }

    public void setCach_time(String cach_time) {
        this.cach_time = cach_time;
    }

    public float getCach_amount() {
        return cach_amount;
    }

    public void setCach_amount(float cach_amount) {
        this.cach_amount = cach_amount;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TiXianHistoryEntity{" +
                "after_balance=" + after_balance +
                ", cach_account='" + cach_account + '\'' +
                ", cach_bark_branch='" + cach_bark_branch + '\'' +
                ", cach_user_name='" + cach_user_name + '\'' +
                ", cach_bark='" + cach_bark + '\'' +
                ", refuse_reason='" + refuse_reason + '\'' +
                ", service_fee=" + service_fee +
                ", before_balance=" + before_balance +
                ", remark='" + remark + '\'' +
                ", cach_no='" + cach_no + '\'' +
                ", pay_time='" + pay_time + '\'' +
                ", pay_trade_no='" + pay_trade_no + '\'' +
                ", cach_time='" + cach_time + '\'' +
                ", cach_amount=" + cach_amount +
                ", user_id=" + user_id +
                ", addtime='" + addtime + '\'' +
                ", admin_id=" + admin_id +
                ", pay_type='" + pay_type + '\'' +
                ", deletestatus=" + deletestatus +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
