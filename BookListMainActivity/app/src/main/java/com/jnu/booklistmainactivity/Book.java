package com.jnu.booklistmainactivity;

public class Book {
    private String name;
    private int coverresourceid;
    public Book(String name,int coverResourceid){
        this.setName(name);
        this.setCoverresourceid(coverResourceid);
    }
    public String getName(){
        return name;
    }
    public int getCoverresourceid(){
        return coverresourceid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoverresourceid(int coverresourceid) {
        this.coverresourceid = coverresourceid;
    }
}
