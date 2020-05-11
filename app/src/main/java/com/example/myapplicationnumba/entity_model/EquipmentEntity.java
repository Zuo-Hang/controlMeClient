package com.example.myapplicationnumba.entity_model;

import java.io.Serializable;

/**
 * EquipmentEntity
 */
public class EquipmentEntity implements Serializable {
    public String imgPath;//图片地址
    public String goodsName;//货物名称
    public String goodsPrice;//货物价格

    public EquipmentEntity() {
    }

    public EquipmentEntity(String imgPath, String goodsName, String goodsPrice) {
        this.imgPath = imgPath;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    public String toString() {
        return "GoodsEntity{" +
                "imgPath='" + imgPath + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                '}';
    }
}

