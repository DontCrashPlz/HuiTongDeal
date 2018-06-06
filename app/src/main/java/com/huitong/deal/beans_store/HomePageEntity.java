package com.huitong.deal.beans_store;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/6/6.
 */

public class HomePageEntity {
//    "bannerlist":Array[2],
//    "floorlist":Array[8],
//    "idcard_auth":0
    private ArrayList<HomePageBannerEntity> bannerlist;//轮播图列表
    private ArrayList<HomePageFloorEntity> floorlist;//楼层列表
    //private int idcard_auth;//实名认证状态

    public ArrayList<HomePageBannerEntity> getBannerlist() {
        return bannerlist;
    }

    public void setBannerlist(ArrayList<HomePageBannerEntity> bannerlist) {
        this.bannerlist = bannerlist;
    }

    public ArrayList<HomePageFloorEntity> getFloorlist() {
        return floorlist;
    }

    public void setFloorlist(ArrayList<HomePageFloorEntity> floorlist) {
        this.floorlist = floorlist;
    }

//    public int getIdcard_auth() {
//        return idcard_auth;
//    }
//
//    public void setIdcard_auth(int idcard_auth) {
//        this.idcard_auth = idcard_auth;
//    }

    @Override
    public String toString() {
        return "HomePageEntity{" +
                "bannerlist=" + bannerlist +
                ", floorlist=" + floorlist +
                '}';
    }
}
