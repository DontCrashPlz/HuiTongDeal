package com.huitong.deal.beans_store;

import java.io.Serializable;

/**
 * Created by Zheng on 2018/6/10.
 */

public class OrderProductEntity implements Serializable {
//    "contraryimgurl":"",
//            "count":1,
//            "coupon":0,
//            "good_spec_id":null,
//            "goods_id":77,
//            "goods_integral":886,
//            "goods_name":"黛乐·公爵干红葡萄酒",
//            "height":800,
//            "id":77,
//            "imgid":432395,
//            "imgtypecode":"imgtype_temp",
//            "imgurl":"http://47.92.94.101/upload/temp/abb03999fae74dc296f830236868dde6.png",
//            "integral":886,
//            "price":886,
//            "price_type":"1",
//            "sale_unit":"瓶",
//            "size":382324,
//            "spec_info":null,
//            "store_price":886,
//            "width":800
    private int count;
    private int goods_id;
    private int goods_integral;
    private String goods_name;
    private String imgurl;
    private int integral;
    private String sale_unit;
    private int store_price;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_integral() {
        return goods_integral;
    }

    public void setGoods_integral(int goods_integral) {
        this.goods_integral = goods_integral;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getSale_unit() {
        return sale_unit;
    }

    public void setSale_unit(String sale_unit) {
        this.sale_unit = sale_unit;
    }

    public int getStore_price() {
        return store_price;
    }

    public void setStore_price(int store_price) {
        this.store_price = store_price;
    }

    @Override
    public String toString() {
        return "OrderProductEntity{" +
                "count=" + count +
                ", goods_id=" + goods_id +
                ", goods_integral=" + goods_integral +
                ", goods_name='" + goods_name + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", integral=" + integral +
                ", sale_unit='" + sale_unit + '\'' +
                ", store_price=" + store_price +
                '}';
    }
}
