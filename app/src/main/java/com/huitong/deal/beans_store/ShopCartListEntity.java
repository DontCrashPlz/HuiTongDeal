package com.huitong.deal.beans_store;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/6/9.
 */

public class ShopCartListEntity {
//    "id":34,
//            "itemlist":Array[2],
//            "total_good":1,
//            "total_integral":886,
//            "total_money":886,
//            "user_id":110
    private ArrayList<ShopCartItemEntity> itemlist;

    public ArrayList<ShopCartItemEntity> getItemlist() {
        return itemlist;
    }

    public void setItemlist(ArrayList<ShopCartItemEntity> itemlist) {
        this.itemlist = itemlist;
    }

    @Override
    public String toString() {
        return "ShopCartListEntity{" +
                "itemlist=" + itemlist +
                '}';
    }
}
