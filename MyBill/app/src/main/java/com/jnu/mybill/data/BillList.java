package com.jnu.mybill.data;

import java.io.Serializable;

public class BillList implements Serializable {
    private int id;
    private String catagory;            //类型
    private String remarks;             //备注
    private int coverreSourceid;        //图片的id
    private double money;               //金额
    private int kind;//0为支出，1为收入   //类型
    private String time;                //时间
    private int year;
    private int month;
    private int day;

    public BillList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public BillList(int id, String catagory, String remarks, int coverreSourceid, double money, int kind, String time, int year, int month, int day) {
        this.id = id;
        this.catagory = catagory;
        this.remarks = remarks;
        this.coverreSourceid = coverreSourceid;
        this.money = money;
        this.kind = kind;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
