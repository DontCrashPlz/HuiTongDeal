package com.huitong.deal.beans;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/5/12.
 */

public class TimeLineEntity {
    private ArrayList<TimeLineDataEntity> datalist;
    private ArrayList<String> datelist;
    private ArrayList<Float> pricelist;
    private ArrayList<Float> ratelist;
    private String stockcode;
    private int stockid;
    private String stockname;

    public ArrayList<TimeLineDataEntity> getDatalist() {
        return datalist;
    }

    public void setDatalist(ArrayList<TimeLineDataEntity> datalist) {
        this.datalist = datalist;
    }

    public ArrayList<String> getDatelist() {
        return datelist;
    }

    public void setDatelist(ArrayList<String> datelist) {
        this.datelist = datelist;
    }

    public ArrayList<Float> getPricelist() {
        return pricelist;
    }

    public void setPricelist(ArrayList<Float> pricelist) {
        this.pricelist = pricelist;
    }

    public ArrayList<Float> getRatelist() {
        return ratelist;
    }

    public void setRatelist(ArrayList<Float> ratelist) {
        this.ratelist = ratelist;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode;
    }

    public int getStockid() {
        return stockid;
    }

    public void setStockid(int stockid) {
        this.stockid = stockid;
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    @Override
    public String toString() {
        return "TimeLineEntity{" +
                "datalist=" + datalist +
                ", datelist=" + datelist +
                ", pricelist=" + pricelist +
                ", ratelist=" + ratelist +
                ", stockcode='" + stockcode + '\'' +
                ", stockid=" + stockid +
                ", stockname='" + stockname + '\'' +
                '}';
    }
}
