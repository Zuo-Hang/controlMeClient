package com.example.myapplicationnumba.entity;

import java.io.Serializable;

/**
 * @Author:zuohang
 * @date:2020/5/22 0022 10:02
 */
public class EquipmentBean implements Serializable {
    //设备名称
    private String equName;
    //设备id
    private Integer equId;
    //设备生产厂家
    private String equManufacturer;
    //设备类型
    private Integer equTypes;
    //设备状态（是否在线）
    private Integer equStatus;
    //设备ip地址
    private String equIp;
    //设备控制点
    private Integer equControlPoint;

    public EquipmentBean(String equName, Integer equId, String equManufacturer, Integer equTypes, Integer equStatus, String equIp, Integer equControlPoint) {
        this.equName = equName;
        this.equId = equId;
        this.equManufacturer = equManufacturer;
        this.equTypes = equTypes;
        this.equStatus = equStatus;
        this.equIp = equIp;
        this.equControlPoint = equControlPoint;
    }

    public EquipmentBean() {
    }

    public String getEquName() {
        return equName;
    }

    public void setEquName(String equName) {
        this.equName = equName;
    }

    public Integer getEquId() {
        return equId;
    }

    public void setEquId(Integer equId) {
        this.equId = equId;
    }

    public String getEquManufacturer() {
        return equManufacturer;
    }

    public void setEquManufacturer(String equManufacturer) {
        this.equManufacturer = equManufacturer;
    }

    public Integer getEquTypes() {
        return equTypes;
    }

    public void setEquTypes(Integer equTypes) {
        this.equTypes = equTypes;
    }

    public Integer getEquStatus() {
        return equStatus;
    }

    public void setEquStatus(Integer equStatus) {
        this.equStatus = equStatus;
    }

    public String getEquIp() {
        return equIp;
    }

    public void setEquIp(String equIp) {
        this.equIp = equIp;
    }

    public Integer getEquControlPoint() {
        return equControlPoint;
    }

    public void setEquControlPoint(Integer equControlPoint) {
        this.equControlPoint = equControlPoint;
    }

    @Override
    public String toString() {
        return "EquipmentBean{" +
                "equName='" + equName + '\'' +
                ", equId=" + equId +
                ", equManufacturer='" + equManufacturer + '\'' +
                ", equTypes=" + equTypes +
                ", equStatus=" + equStatus +
                ", equIp='" + equIp + '\'' +
                ", equControlPoint=" + equControlPoint +
                '}';
    }
}
