package com.suyihang.entity;

import com.suyihang.database.DataSelectBook;

public class Type {

    private int typeID;
    private String typeName;
    private int typeNum;
    private int typeLendNum;

    public Type(int typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.typeNum = DataSelectBook.findTypeIDinBookTable(typeID);
        this.typeLendNum = DataSelectBook.findTypeIDinBorrowTable(typeID);
    }

    public Type() {}

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(int typeNum) {
        this.typeNum = typeNum;
    }

    public int getTypeLendNum() {
        return typeLendNum;
    }

    public void setTypeLendNum(int typeLendNum) {
        this.typeLendNum = typeLendNum;
    }

    @Override
    public String toString() {
        return "Type{" +
                "typeID=" + typeID +
                ", typeName='" + typeName + '\'' +
                ", typeNum=" + typeNum +
                ", typeLendNum=" + typeLendNum +
                '}';
    }
}
