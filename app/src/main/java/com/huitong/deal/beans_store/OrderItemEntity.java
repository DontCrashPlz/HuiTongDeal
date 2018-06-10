package com.huitong.deal.beans_store;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Zheng on 2018/6/10.
 */

public class OrderItemEntity implements Serializable {
    private int addr_id;
    private String addtime;
    private String create_time;
    private ArrayList<OrderProductEntity> goodlist;
    private int goods_amount;
    private int id;
    private int invoicetype;
    private String msg;
    private String order_id;
    private int order_status;
    private int totalintegral;
    private int totalprice;
    private int user_id;

    public int getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(int addr_id) {
        this.addr_id = addr_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public ArrayList<OrderProductEntity> getGoodlist() {
        return goodlist;
    }

    public void setGoodlist(ArrayList<OrderProductEntity> goodlist) {
        this.goodlist = goodlist;
    }

    public int getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(int goods_amount) {
        this.goods_amount = goods_amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoicetype() {
        return invoicetype;
    }

    public void setInvoicetype(int invoicetype) {
        this.invoicetype = invoicetype;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getTotalintegral() {
        return totalintegral;
    }

    public void setTotalintegral(int totalintegral) {
        this.totalintegral = totalintegral;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "OrderItemEntity{" +
                "addr_id=" + addr_id +
                ", addtime='" + addtime + '\'' +
                ", create_time='" + create_time + '\'' +
                ", goodlist=" + goodlist +
                ", goods_amount=" + goods_amount +
                ", id=" + id +
                ", invoicetype=" + invoicetype +
                ", msg='" + msg + '\'' +
                ", order_id='" + order_id + '\'' +
                ", order_status=" + order_status +
                ", totalintegral=" + totalintegral +
                ", totalprice=" + totalprice +
                ", user_id=" + user_id +
                '}';
    }
}
