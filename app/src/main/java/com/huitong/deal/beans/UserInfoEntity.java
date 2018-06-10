package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/6.
 */

public class UserInfoEntity {

//            "address":null,
//            "addressareaid":null,
//            "addtime":"2018-06-01 11:51:03",
//            "availablebalance":1000000,
//            "birthday":null,
//            "credit":0,
//            "freezeblance":null,
//            "headid":null,
//            "headimgurl":"http://47.92.94.101/public/img/user-default.png",
//            "id":110,
//            "idcardno":null,
//            "integral":100000,
//            "lastlogindate":null,
//            "lastloginip":null,
//            "logincount":null,
//            "parent_id":null,
//            "qq_openid":null,
//            "sex":0,
//            "telephone":null

    private String address;
    private String addressareaid;
    private String addtime;
    private float availablebalance;
    private String birthday;
    private int credit;
    private String freezeblance;
    private String headid;
    private String headimgurl;
    private int id;
    private String idcardno;
    private float integral;
    private String lastlogindate;
    private String lastloginip;
    private String logincount;
    private String parent_id;
    private String qq_openid;
    private int sex;
    private String telephone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressareaid() {
        return addressareaid;
    }

    public void setAddressareaid(String addressareaid) {
        this.addressareaid = addressareaid;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public float getAvailablebalance() {
        return availablebalance;
    }

    public void setAvailablebalance(float availablebalance) {
        this.availablebalance = availablebalance;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getFreezeblance() {
        return freezeblance;
    }

    public void setFreezeblance(String freezeblance) {
        this.freezeblance = freezeblance;
    }

    public String getHeadid() {
        return headid;
    }

    public void setHeadid(String headid) {
        this.headid = headid;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public float getIntegral() {
        return integral;
    }

    public void setIntegral(float integral) {
        this.integral = integral;
    }

    public String getLastlogindate() {
        return lastlogindate;
    }

    public void setLastlogindate(String lastlogindate) {
        this.lastlogindate = lastlogindate;
    }

    public String getLastloginip() {
        return lastloginip;
    }

    public void setLastloginip(String lastloginip) {
        this.lastloginip = lastloginip;
    }

    public String getLogincount() {
        return logincount;
    }

    public void setLogincount(String logincount) {
        this.logincount = logincount;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getQq_openid() {
        return qq_openid;
    }

    public void setQq_openid(String qq_openid) {
        this.qq_openid = qq_openid;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "address='" + address + '\'' +
                ", addressareaid='" + addressareaid + '\'' +
                ", addtime='" + addtime + '\'' +
                ", availablebalance=" + availablebalance +
                ", birthday='" + birthday + '\'' +
                ", credit=" + credit +
                ", freezeblance='" + freezeblance + '\'' +
                ", headid='" + headid + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", id=" + id +
                ", idcardno='" + idcardno + '\'' +
                ", integral=" + integral +
                ", lastlogindate='" + lastlogindate + '\'' +
                ", lastloginip='" + lastloginip + '\'' +
                ", logincount='" + logincount + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", qq_openid='" + qq_openid + '\'' +
                ", sex=" + sex +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
