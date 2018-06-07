package com.huitong.deal.beans_store;

/**
 * Created by Zheng on 2018/6/7.
 */

public class AddressEntity {
//            "address":"朝阳大道",
//            "addtime":"2018-06-07 22:01:15",
//            "area_all_name":"河南省,郑州市,金水区",
//            "area_id":4523661,
//            "area_name":"河南省郑州市金水区",
//            "deletestatus":false,
//            "id":32817,
//            "isdefault":false,
//            "mobile":"13523005529",
//            "recvname":"测试",
//            "telephone":null,
//            "user_id":110,
//            "zip":"450000"
    private int id;
    private String area_name;
    private String zip;
    private String address;
    private String recvname;
    private String mobile;
    private boolean isdefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRecvname() {
        return recvname;
    }

    public void setRecvname(String recvname) {
        this.recvname = recvname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isIsdefault() {
        return isdefault;
    }

    public void setIsdefault(boolean isdefault) {
        this.isdefault = isdefault;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id=" + id +
                ", area_name='" + area_name + '\'' +
                ", zip='" + zip + '\'' +
                ", address='" + address + '\'' +
                ", recvname='" + recvname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", isdefault=" + isdefault +
                '}';
    }
}
