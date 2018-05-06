package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/6.
 */

public class VerificationCodeEntity {
    private String receivemobile;
    private String systemcode;
    private int effectivetime;
    private String addtime;
    private String smscode;
    private String usertype;
    private String invalidtime;
    private int id;
    private String usetype;

    public String getReceivemobile() {
        return receivemobile;
    }

    public void setReceivemobile(String receivemobile) {
        this.receivemobile = receivemobile;
    }

    public String getSystemcode() {
        return systemcode;
    }

    public void setSystemcode(String systemcode) {
        this.systemcode = systemcode;
    }

    public int getEffectivetime() {
        return effectivetime;
    }

    public void setEffectivetime(int effectivetime) {
        this.effectivetime = effectivetime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getInvalidtime() {
        return invalidtime;
    }

    public void setInvalidtime(String invalidtime) {
        this.invalidtime = invalidtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsetype() {
        return usetype;
    }

    public void setUsetype(String usetype) {
        this.usetype = usetype;
    }

    @Override
    public String toString() {
        return "VerificationCodeEntity{" +
                "receivemobile='" + receivemobile + '\'' +
                ", systemcode='" + systemcode + '\'' +
                ", effectivetime=" + effectivetime +
                ", addtime='" + addtime + '\'' +
                ", smscode='" + smscode + '\'' +
                ", usertype='" + usertype + '\'' +
                ", invalidtime='" + invalidtime + '\'' +
                ", id=" + id +
                ", usetype='" + usetype + '\'' +
                '}';
    }
}
