package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/11.
 */

public class LeverageEntity {
    //止损率
    private float loseRate;
    //杠杆显示名称
    private String leverageName;
    //杠杆
    private int leverage;
    //产品名称
    private String stockName;
    //价格(杠杆对应的价格，作为buyPrice使用)
    private float price;
    //产品id
    private int stockId;
    //止盈率
    private float gainRate;
    //pointPrice
    private  float pointPrice;
    //平仓类型，1自动，2，手动
    private int closeType;
    //服务费率
    private float feeRate;
    //产品编号
    private String stockCode;

    public float getLoseRate() {
        return loseRate;
    }

    public void setLoseRate(float loseRate) {
        this.loseRate = loseRate;
    }

    public String getLeverageName() {
        return leverageName;
    }

    public void setLeverageName(String leverageName) {
        this.leverageName = leverageName;
    }

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public float getGainRate() {
        return gainRate;
    }

    public void setGainRate(float gainRate) {
        this.gainRate = gainRate;
    }

    public float getPointPrice() {
        return pointPrice;
    }

    public void setPointPrice(float pointPrice) {
        this.pointPrice = pointPrice;
    }

    public int getCloseType() {
        return closeType;
    }

    public void setCloseType(int closeType) {
        this.closeType = closeType;
    }

    public float getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(float feeRate) {
        this.feeRate = feeRate;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    @Override
    public String toString() {
        return "LeverageEntity{" +
                "loseRate=" + loseRate +
                ", leverageName='" + leverageName + '\'' +
                ", leverage=" + leverage +
                ", stockName='" + stockName + '\'' +
                ", price=" + price +
                ", stockId=" + stockId +
                ", gainRate=" + gainRate +
                ", pointPrice=" + pointPrice +
                ", closeType=" + closeType +
                ", feeRate=" + feeRate +
                ", stockCode='" + stockCode + '\'' +
                '}';
    }
}
