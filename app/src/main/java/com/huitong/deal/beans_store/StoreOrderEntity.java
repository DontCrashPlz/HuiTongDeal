package com.huitong.deal.beans_store;

/**
 * Created by Zheng on 2018/6/10.
 */

public class StoreOrderEntity {
    //{"order_id":141,"order_no":"I11020180610023439"}
    private int order_id;
    private String order_no;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    @Override
    public String toString() {
        return "StoreOrderEntity{" +
                "order_id=" + order_id +
                ", order_no='" + order_no + '\'' +
                '}';
    }
}
