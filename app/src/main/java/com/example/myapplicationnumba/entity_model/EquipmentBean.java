package com.example.myapplicationnumba.entity_model;

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
    //设备状态（开启或关闭）
    private Integer equStatus;

    private String equIp;

    private String equNotes;

    public EquipmentBean() {
    }

    public EquipmentBean(String equIp) {
        this.equIp = equIp;
        equName = "小米电视i7745";
        equManufacturer = "小米";
        equTypes = 1;
        equStatus = 0;
    }

    public EquipmentBean(String equName, Integer equId, String equManufacturer, Integer equTypes, Integer equStatus, String equIp, String equNotes) {
        this.equName = equName;
        this.equId = equId;
        this.equManufacturer = equManufacturer;
        this.equTypes = equTypes;
        this.equStatus = equStatus;
        this.equIp = equIp;
        this.equNotes = equNotes;
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

    public String getEquNotes() {
        return equNotes;
    }

    public void setEquNotes(String equNotes) {
        this.equNotes = equNotes;
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
                ", equNotes='" + equNotes + '\'' +
                '}';
    }
}
