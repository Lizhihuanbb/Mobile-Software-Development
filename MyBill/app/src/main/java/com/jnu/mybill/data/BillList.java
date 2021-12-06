package com.jnu.mybill.data;

import java.io.Serializable;

public class BillList implements Serializable {
    private String catagory;
    private String remarks;
    private int coverreSourceid;
    private double money;

    public BillList(String catagory, String note, int coverreSourceid, double amounts) {
        this.catagory = catagory;
        this.remarks =note;
        this.coverreSourceid=coverreSourceid;
        this.money =amounts;
    }


    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getCoverreSourceid() {
        return coverreSourceid;
    }

    public void setCoverreSourceid(int coverreSourceid) {
        this.coverreSourceid = coverreSourceid;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
