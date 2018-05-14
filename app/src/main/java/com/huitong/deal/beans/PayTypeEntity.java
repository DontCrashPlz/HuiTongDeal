package com.huitong.deal.beans;

/**
 * Created by Zheng on 2018/5/14.
 */

public class PayTypeEntity {
    //是否可用 1可用.0不可用
    private int install;
    //支付名称
    private String name;
    //支付类型
    private String paytype;
    //终端类型
    private String terminaltype;

    public int getInstall() {
        return install;
    }

    public void setInstall(int install) {
        this.install = install;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getTerminaltype() {
        return terminaltype;
    }

    public void setTerminaltype(String terminaltype) {
        this.terminaltype = terminaltype;
    }

    @Override
    public String toString() {
        return "PayTypeEntity{" +
                "install=" + install +
                ", name='" + name + '\'' +
                ", paytype='" + paytype + '\'' +
                ", terminaltype='" + terminaltype + '\'' +
                '}';
    }
}
