package com.example.myapplicationnumba.activitys.find;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.entity.EquipmentBean;
import com.example.myapplicationnumba.util.HttpUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Response;

/**
 * 扫描到设备后的设置、上传设备信息的页面
 */
public class SettingDeviceActivity extends AppCompatActivity {
    private List<EquipmentBean> equipmentEntities;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_device);
        equipmentEntities = (List<EquipmentBean>) getIntent().getSerializableExtra("arrayList");
        init();
    }

    private void init(){
        //equipmentEntities = (List<EquipmentBean>) getIntent().getSerializableExtra("arrayList");
        EquipmentBean equipmentBean = equipmentEntities.get(0);
        TextView textView =(TextView) findViewById(R.id.name);
        textView.setText(equipmentBean.getEquName());
        TextView equManufacturer =(TextView) findViewById(R.id.equManufacturer);
        equManufacturer.setText(equipmentBean.getEquManufacturer());
        button = findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.43.198:8080/getAll";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FormBody.Builder formBodyBuild= new FormBody.Builder();
                        //上传的时候需要转为json或者字符串的形式才可以上传
                        //formBodyBuild.add("equipmentBean",equipmentEntities.get(0));//此处添加所需要提交的参数
                        String responseData = null;
                        Response response = HttpUtil.sendOkHttpGetRequest(url);
                        try {
                            responseData = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //将字符串转jsonObj
                        JsonObject asJsonObject = new JsonParser().parse(responseData).getAsJsonObject();
                        JsonElement errorCode = asJsonObject.get("errorCode");
                        if(errorCode.toString().equals(200)){
                            //弹出添加成功
                        }else{
                            //弹出添加失败
                        }

                    }
                }).start();
            }
        });
    }
}
