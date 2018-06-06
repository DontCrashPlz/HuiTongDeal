package com.huitong.deal.beans_store;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/6/6.
 */

public class ProductDetailEntity {
    private String goods_brief;//商品描述
    private String goods_details;//商品详情
    private int goods_integral;//提货券价格
    private int goods_inventory;//库存
    private String goods_name;//商品名称
    private String goods_name_english;//商品英文名称
    private int goods_salenum;//已售数量
    private int id;
    private String imgurl;//主图url
    private ArrayList<String> imgurllist;//轮播图列表
    private String manufacturer;//制造商
    private ArrayList<ProductParamEntity> paramlist;//商品参数列表
    private String sale_unit;//商品单位
    private int store_price;//购物券价格

    public String getGoods_brief() {
        return goods_brief;
    }

    public void setGoods_brief(String goods_brief) {
        this.goods_brief = goods_brief;
    }

    public String getGoods_details() {
        return goods_details;
    }

    public void setGoods_details(String goods_details) {
        this.goods_details = goods_details;
    }

    public int getGoods_integral() {
        return goods_integral;
    }

    public void setGoods_integral(int goods_integral) {
        this.goods_integral = goods_integral;
    }

    public int getGoods_inventory() {
        return goods_inventory;
    }

    public void setGoods_inventory(int goods_inventory) {
        this.goods_inventory = goods_inventory;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_name_english() {
        return goods_name_english;
    }

    public void setGoods_name_english(String goods_name_english) {
        this.goods_name_english = goods_name_english;
    }

    public int getGoods_salenum() {
        return goods_salenum;
    }

    public void setGoods_salenum(int goods_salenum) {
        this.goods_salenum = goods_salenum;
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

    public ArrayList<String> getImgurllist() {
        return imgurllist;
    }

    public void setImgurllist(ArrayList<String> imgurllist) {
        this.imgurllist = imgurllist;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public ArrayList<ProductParamEntity> getParamlist() {
        return paramlist;
    }

    public void setParamlist(ArrayList<ProductParamEntity> paramlist) {
        this.paramlist = paramlist;
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
        return "ProductDetailEntity{" +
                "goods_brief='" + goods_brief + '\'' +
                ", goods_details='" + goods_details + '\'' +
                ", goods_integral=" + goods_integral +
                ", goods_inventory=" + goods_inventory +
                ", goods_name='" + goods_name + '\'' +
                ", goods_name_english='" + goods_name_english + '\'' +
                ", goods_salenum=" + goods_salenum +
                ", id=" + id +
                ", imgurl='" + imgurl + '\'' +
                ", imgurllist=" + imgurllist +
                ", manufacturer='" + manufacturer + '\'' +
                ", paramlist=" + paramlist +
                ", sale_unit='" + sale_unit + '\'' +
                ", store_price=" + store_price +
                '}';
    }
}
