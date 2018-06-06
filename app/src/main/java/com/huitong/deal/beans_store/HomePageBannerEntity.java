package com.huitong.deal.beans_store;

/**
 * Created by Zheng on 2018/6/6.
 */

public class HomePageBannerEntity {
//    "imgUrl":"http://192.168.1.244:8040/upload/temp/932d1fe13d084f7396b3d8a72a87c861.png",
//    "good_id":"78",
//    "url":"http://47.92.28.185/wap/integral/goods/detail?id=78"
    private String imgUrl;
    private String good_id;
    private String url;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGood_id() {
        return good_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "HomePageBannerEntity{" +
                "imgUrl='" + imgUrl + '\'' +
                ", good_id='" + good_id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
