package com.jnu.mybill.data;

public class BillType {
    private int id;                 //BillType表中的主属性
    private String typeName;        //类型的名称
    private int coverResourceId;    //图片的ID
    private int kind;                       //0为支出，1为收入

    public BillType(int id, String typeName, int coverResourceId, int kind) {
        this.id = id;
        this.typeName = typeName;
        this.coverResourceId = coverResourceId;
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public void setCoverResourceId(int coverResourceId) {
        this.coverResourceId = coverResourceId;
    }
}
