package com.huitong.deal.beans_store;

import com.huitong.deal.beans.UserOrderInfoEntity;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/6/10.
 */

public class OrderListEntity {
    private boolean last;
    private boolean first;
    private ArrayList<OrderItemEntity> list= new ArrayList<>();
    private UserOrderInfoEntity extData;

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public ArrayList<OrderItemEntity> getList() {
        return list;
    }

    public void setList(ArrayList<OrderItemEntity> list) {
        this.list = list;
    }

    public UserOrderInfoEntity getExtData() {
        return extData;
    }

    public void setExtData(UserOrderInfoEntity extData) {
        this.extData = extData;
    }

    @Override
    public String toString() {
        return "OrderListEntity{" +
                "last=" + last +
                ", first=" + first +
                ", list=" + list +
                ", extData=" + extData +
                '}';
    }
}
