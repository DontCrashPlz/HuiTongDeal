package com.huitong.deal.beans;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/5/12.
 */

public class KLineEntity {
    private ArrayList<ArrayList<String>> klinelist;
    private ArrayList<String> m5list;
    private String stockcode;
    private int stockid;
    private String stockname;

    public ArrayList<ArrayList<String>> getKlinelist() {
        return klinelist;
    }

    public void setKlinelist(ArrayList<ArrayList<String>> klinelist) {
        this.klinelist = klinelist;
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

    public ArrayList<String> getM5list() {
        return m5list;
    }

    public void setM5list(ArrayList<String> m5list) {
        this.m5list = m5list;
    }

    @Override
    public String toString() {
        return "KLineEntity{" +
                "klinelist=" + klinelist +
                ", m5list=" + m5list +
                ", stockcode='" + stockcode + '\'' +
                ", stockid=" + stockid +
                ", stockname='" + stockname + '\'' +
                '}';
    }
}
