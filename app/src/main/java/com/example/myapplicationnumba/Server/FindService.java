package com.example.myapplicationnumba.Server;

import com.example.myapplicationnumba.activitys.find.findActivity;
import com.example.myapplicationnumba.entity.EquipmentBean;

import java.util.List;

/**
 * 发现服务功能接口
 */
public interface FindService {
    /**
     * 通过局域网广播发现周边设备
     */
    List<EquipmentBean> findByBroadcast(findActivity activity);

    /**
     * 通过二维码发现周边设备
     */
    void findByQRImage();
}
