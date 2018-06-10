package com.huitong.deal.beans_store;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/6/9.
 */

public class ShopCartEntity {
//    "cart_goods_count":3,
//    "cart_list":Array[1]
    private int cart_goods_count;//购物车商品总数
    private ArrayList<ShopCartListEntity> cart_list;//购物车列表

    public int getCart_goods_count() {
        return cart_goods_count;
    }

    public void setCart_goods_count(int cart_goods_count) {
        this.cart_goods_count = cart_goods_count;
    }

    public ArrayList<ShopCartListEntity> getCart_list() {
        return cart_list;
    }

    public void setCart_list(ArrayList<ShopCartListEntity> cart_list) {
        this.cart_list = cart_list;
    }

    @Override
    public String toString() {
        return "ShopCartEntity{" +
                "cart_goods_count=" + cart_goods_count +
                ", cart_list=" + cart_list +
                '}';
    }
}
