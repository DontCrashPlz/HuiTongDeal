package com.huitong.deal.beans_store;

/**
 * Created by Zheng on 2018/6/6.
 */

public class HomePageCommodityEntity {
//    "contraryimgurl":"",
//    "goods_integral":1600,
//    "goods_name":"那卡一级",
//    "id":95,
//    "imgsmallurl":"http://192.168.1.244:8040/upload/temp/022638811b46477e9f3dbecc72edfdde.jpg",
//    "imgurl":"http://192.168.1.244:8040/upload/temp/022638811b46477e9f3dbecc72edfdde.jpg",
//    "store_price":1600
    private int id;
    private String imgurl;//图片url
    private String goods_name;//商品名称
    private int store_price;//购物券价格
    private int goods_integral;//提货券价格

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getStore_price() {
        return store_price;
    }

    public void setStore_price(int store_price) {
        this.store_price = store_price;
    }

    public int getGoods_integral() {
        return goods_integral;
    }

    public void setGoods_integral(int goods_integral) {
        this.goods_integral = goods_integral;
    }

    @Override
    public String toString() {
        return "HomePageCommodityEntity{" +
                "id=" + id +
                ", imgurl='" + imgurl + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", store_price=" + store_price +
                ", goods_integral=" + goods_integral +
                '}';
    }
}
