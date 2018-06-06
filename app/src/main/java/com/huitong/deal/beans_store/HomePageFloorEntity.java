package com.huitong.deal.beans_store;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/6/6.
 */

public class HomePageFloorEntity {
//    "advertpos_id":null,
//    "aptitle":null,
//    "floorname":"那卡",
//    "goodslist":[Object{...},Object{...}],
//    "grade":2,
//    "icon_acc_id":null,
//    "icon_background":null,
//    "icon_type":24,
//    "id":10,
//    "objclass_id":64,
//    "objtype":null,
//    "parent_id":2,
//    "seq":4
    private int id;//楼层id
    private String floorname;//楼层名称
    private ArrayList<HomePageCommodityEntity> goodslist;//楼层子列表

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFloorname() {
        return floorname;
    }

    public void setFloorname(String floorname) {
        this.floorname = floorname;
    }

    public ArrayList<HomePageCommodityEntity> getGoodslist() {
        return goodslist;
    }

    public void setGoodslist(ArrayList<HomePageCommodityEntity> goodslist) {
        this.goodslist = goodslist;
    }

    @Override
    public String toString() {
        return "HomePageFloorEntity{" +
                "id=" + id +
                ", floorname='" + floorname + '\'' +
                ", goodslist=" + goodslist +
                '}';
    }
}
