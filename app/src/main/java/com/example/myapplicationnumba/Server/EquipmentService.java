package com.example.myapplicationnumba.Server;

import com.example.myapplicationnumba.activitys.base.MainActivity;
import com.example.myapplicationnumba.activitys.control.DeviceInformationActivity;
import com.example.myapplicationnumba.activitys.control.ManagementEquipmentActivity;
import com.example.myapplicationnumba.activitys.find.SettingDeviceActivity;
import com.example.myapplicationnumba.entity.EquipmentBean;

public interface EquipmentService {
    /**
     * 加载我的设备列表
     *
     */
    void loadMyEquipment(MainActivity activity);

    /**
     * 添加新设备
     * @param bean
     */
    void addEquipment(EquipmentBean bean,String notes, SettingDeviceActivity activity);

    /**
     * 删除我的设备
     * @param equipmentId
     */
    void moveOutMyEquipment(int equipmentId, DeviceInformationActivity activity);

    /**
     * 管理我的设备
     * @param equipmentId
     */
    void managementMyEquipment(int equipmentId);

    void control(int equipmentId, ManagementEquipmentActivity activity);

    void upDate();

}
