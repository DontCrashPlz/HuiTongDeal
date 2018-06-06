package com.huitong.deal.beans_store;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/6/6.
 */

public class StoreListEntity {

    private boolean last;
    private ArrayList<HomePageCommodityEntity> list;

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
        return "StoreListEntity{" +
                "last=" + last +
                ", list=" + list +
                '}';
    }
}
