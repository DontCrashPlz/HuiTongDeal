package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/6.
 */

public class UserInfoDataEntity {
    private String truename;
    private String ec_salt;
    private int level;
    private String pay_salt;
    private String userno;
    private String mobile;
    private int usertype;
    private String userrole;
    private String lockdate;
    private int parentid;
    private String register_type;
    private String password;
    private String addtime;
    private String pay_password;
    private String nickname;
    private boolean deletestatus;
    private int errorcount;
    private String froms;
    private int id;
    private String email;
    private UserInfoEntity userinfo;
    private int status;
    private String username;
    private UserOrderInfoEntity orderstatusstats;

    public UserOrderInfoEntity getOrderstatusstats() {
        return orderstatusstats;
    }

    public void setOrderstatusstats(UserOrderInfoEntity orderstatusstats) {
        this.orderstatusstats = orderstatusstats;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getEc_salt() {
        return ec_salt;
    }

    public void setEc_salt(String ec_salt) {
        this.ec_salt = ec_salt;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPay_salt() {
        return pay_salt;
    }

    public void setPay_salt(String pay_salt) {
        this.pay_salt = pay_salt;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getLockdate() {
        return lockdate;
    }

    public void setLockdate(String lockdate) {
        this.lockdate = lockdate;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getRegister_type() {
        return register_type;
    }

    public void setRegister_type(String register_type) {
        this.register_type = register_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPay_password() {
        return pay_password;
    }

    public void setPay_password(String pay_password) {
        this.pay_password = pay_password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isDeletestatus() {
        return deletestatus;
    }

    public void setDeletestatus(boolean deletestatus) {
        this.deletestatus = deletestatus;
    }

    public int getErrorcount() {
        return errorcount;
    }

    public void setErrorcount(int errorcount) {
        this.errorcount = errorcount;
    }

    public String getFroms() {
        return froms;
    }

    public void setFroms(String froms) {
        this.froms = froms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserInfoEntity getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoEntity userinfo) {
        this.userinfo = userinfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserInfoDataEntity{" +
                "truename='" + truename + '\'' +
                ", ec_salt='" + ec_salt + '\'' +
                ", level=" + level +
                ", pay_salt='" + pay_salt + '\'' +
                ", userno='" + userno + '\'' +
                ", mobile='" + mobile + '\'' +
                ", usertype=" + usertype +
                ", userrole='" + userrole + '\'' +
                ", lockdate='" + lockdate + '\'' +
                ", parentid=" + parentid +
                ", register_type='" + register_type + '\'' +
                ", password='" + password + '\'' +
                ", addtime='" + addtime + '\'' +
                ", pay_password='" + pay_password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", deletestatus=" + deletestatus +
                ", errorcount=" + errorcount +
                ", froms='" + froms + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", userinfo=" + userinfo +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", orderstatusstats=" + orderstatusstats +
                '}';
    }
}
