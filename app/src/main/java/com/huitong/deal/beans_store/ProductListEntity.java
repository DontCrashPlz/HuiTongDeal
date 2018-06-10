package com.huitong.deal.beans_store;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/6/9.
 */

public class ProductListEntity {
    private boolean first;//判断是否为第一页
    private boolean last;//判断是否为最后一页
    private ArrayList<HomePageCommodityEntity> list;//商品列表

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public ArrayList<HomePageCommodityEntity> getList() {
        return list;
    }

    public void setList(ArrayList<HomePageCommodityEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ProductListEntity{" +
                "first=" + first +
                ", last=" + last +
                ", list=" + list +
                '}';
    }
}
