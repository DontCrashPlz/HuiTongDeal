package com.huitong.deal.beans_store;

import java.io.Serializable;

/**
 * Created by Zheng on 2018/6/9.
 */

public class ShopCartItemEntity implements Serializable {
//            "count":2,
//            "goods_id":78,
//            "goods_name":"白马城堡干红葡萄酒",
//            "id":341,
//            "imgsmallurl":"http://47.92.94.101/upload/temp/f49e15c709534083a65ee7419530cc48.png",
//            "imgurl":"http://47.92.94.101/upload/temp/f49e15c709534083a65ee7419530cc48.png",
//            "integral":768,
//            "price":768,
//            "sale_unit":null,
//            "subtotalcash":1536,
//            "subtotalintegral":1536
    private int count;//商品数量
    private int goods_id;//商品id
    private String goods_name;//商品名称
    private int id;//购物车条目id
    private String imgurl;//图片url
    private int integral;//提货券单价
    private int price;//购物券单价
    private int subtotalcash;//购物券小计
    private int subtotalintegral;//提货券小计
    private String sale_unit;

    public String getSale_unit() {
        return sale_unit;
    }

    public void setSale_unit(String sale_unit) {
        this.sale_unit = sale_unit;
    }

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

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

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

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSubtotalcash() {
        return subtotalcash;
    }

    public void setSubtotalcash(int subtotalcash) {
        this.subtotalcash = subtotalcash;
    }

    public int getSubtotalintegral() {
        return subtotalintegral;
    }

    public void setSubtotalintegral(int subtotalintegral) {
        this.subtotalintegral = subtotalintegral;
    }

    @Override
    public String toString() {
        return "ShopCartItemEntity{" +
                "count=" + count +
                ", goods_id=" + goods_id +
                ", goods_name='" + goods_name + '\'' +
                ", id=" + id +
                ", imgurl='" + imgurl + '\'' +
                ", integral=" + integral +
                ", price=" + price +
                ", subtotalcash=" + subtotalcash +
                ", subtotalintegral=" + subtotalintegral +
                ", sale_unit='" + sale_unit + '\'' +
                '}';
    }
}
