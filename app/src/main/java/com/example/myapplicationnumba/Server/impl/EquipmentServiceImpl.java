package com.example.myapplicationnumba.Server.impl;

import android.os.Message;

import com.example.myapplicationnumba.Server.EquipmentService;
import com.example.myapplicationnumba.activitys.base.MainActivity;
import com.example.myapplicationnumba.activitys.control.DeviceInformationActivity;
import com.example.myapplicationnumba.activitys.control.ManagementEquipmentActivity;
import com.example.myapplicationnumba.activitys.find.SettingDeviceActivity;
import com.example.myapplicationnumba.base.MyApplication;
import com.example.myapplicationnumba.entity.EquipmentBean;
import com.example.myapplicationnumba.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Response;

public class EquipmentServiceImpl implements EquipmentService {

    private ArrayList<EquipmentBean> equipmentBeanArrayList;

    /**
     * 加载我的设备列表
     */
    @Override
    public void loadMyEquipment(MainActivity activity) {
        equipmentBeanArrayList = new ArrayList<EquipmentBean>();
        String url = "http://192.168.43.198:8080/getAll";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String responseData = null;
                Response response = HttpUtil.sendOkHttpGetRequest(url);
                try {
                    responseData = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //将字符串转jsonObj
                JsonObject asJsonObject = new JsonParser().parse(responseData).getAsJsonObject();
                JsonElement data = asJsonObject.get("data");
                Gson gson = new Gson();
                equipmentBeanArrayList = gson.fromJson(data, new TypeToken<ArrayList<EquipmentBean>>(){}.getType());
                //notifyAdapter(equipmentBeanArrayList);
                Message message = new Message();
                message.what = 2;
                message.obj = equipmentBeanArrayList;
                activity.handler.sendMessage(message);
            }
        }).start();
    }
    /**
     * 添加新设备
     * @param bean
     */
    @Override
    public void addEquipment(EquipmentBean bean,String notes, SettingDeviceActivity activity) {
        FormBody.Builder formBodyBuild = new FormBody.Builder();
        formBodyBuild.add("equName",bean.getEquName());//此处添加所需要提交的参数
        formBodyBuild.add("equManufacturer",bean.getEquManufacturer());//此处添加所需要提交的参数
        formBodyBuild.add("equTypes",bean.getEquTypes().toString());//此处添加所需要提交的参数
        formBodyBuild.add("equStatus",bean.getEquStatus().toString());//此处添加所需要提交的参数
        formBodyBuild.add("equIp",bean.getEquIp());//此处添加所需要提交的参数
        formBodyBuild.add("notes",notes);//此处添加所需要提交的参数
        formBodyBuild.add("id", MyApplication.sysUser.getId().toString());//此处添加所需要提交的参数
        String urlAddress = "http://192.168.43.198:8080/addEquipment";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = HttpUtil.sendOkHttpPostRequest(urlAddress,formBodyBuild);
                    Headers headers = response.headers();
                    //获取到cookies
                    List<String> cookies=headers.values("Set-Cookie");
                    if (response != null) {
                        String responseData = response.body().string();
                        Map<String, Object> map = null;
                        //使用Gson解析服务器返回的数据
                        Gson gson = new Gson();
                        Type type = new TypeToken<Map<String, Object>>() {
                        }.getType();
                        map = gson.fromJson(responseData, type);
                        String msg = map.get("errorMsg").toString();
                        //判断成功还是失败，并向主线程传递数据
                        if ("成功".equals(msg)) {
                            Message message = new Message();
                            message.what = 1;
                            message.obj = msg;
                            activity.handler.sendMessage(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 删除我的设备
     * @param equipmentId
     */
    @Override
    public void moveOutMyEquipment(int equipmentId, DeviceInformationActivity activity) {
        String url = "http://192.168.43.198:8080/deleteById";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = HttpUtil.sendOkHttpGetRequest(url+"?id="+equipmentId);
                try {
                    if (response != null) {
                        String responseData = response.body().string();
                        Map<String, Object> map = null;
                        //使用Gson解析服务器返回的数据
                        Gson gson = new Gson();
                        Type type = new TypeToken<Map<String, Object>>() {
                        }.getType();
                        map = gson.fromJson(responseData, type);
                        String msg = map.get("errorMsg").toString();

                        //判断成功还是失败，并向主线程传递数据
                        if ("成功".equals(msg)) {
                            Message message = new Message();
                            message.what = 1;
                            //message.obj = msg;
                            activity.handler.sendMessage(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 管理我的设备
     * @param equipmentId
     */
    @Override
    public void managementMyEquipment(int equipmentId) {

    }

    @Override
    public void control(int equipmentId, ManagementEquipmentActivity activity) {
        String url = "http://192.168.43.198:8080/control";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = HttpUtil.sendOkHttpGetRequest(url+"?id="+equipmentId);
                String responseData = null;
                try {
                    if (response != null) {
                        try {
                            responseData = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //将字符串转jsonObj
                        JsonObject asJsonObject = new JsonParser().parse(responseData).getAsJsonObject();
                        JsonElement data = asJsonObject.get("data");
                        String s = data.toString();
                            Message message = new Message();
                            message.what = 1;
                           // message.obj
                            message.obj = s;
                           activity.handler.sendMessage(message);
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void upDate() {

    }
}
