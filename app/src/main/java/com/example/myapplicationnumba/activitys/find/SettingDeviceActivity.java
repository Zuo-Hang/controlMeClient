package com.example.myapplicationnumba.activitys.find;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.Server.EquipmentService;
import com.example.myapplicationnumba.Server.impl.EquipmentServiceImpl;
import com.example.myapplicationnumba.activitys.base.MainActivity;
import com.example.myapplicationnumba.activitys.base.SplashActivity;
import com.example.myapplicationnumba.base.MyApplication;
import com.example.myapplicationnumba.entity.EquipmentBean;
import com.example.myapplicationnumba.util.HttpUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Response;

/**
 * 扫描到设备后的设置、上传设备信息的页面
 */
public class SettingDeviceActivity extends AppCompatActivity {
    private List<EquipmentBean> equipmentEntities;
    Button button;
    public EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_device);
        equipmentEntities = (List<EquipmentBean>) getIntent().getSerializableExtra("arrayList");
        init();
    }

    private void init(){
        EquipmentBean equipmentBean = equipmentEntities.get(0);
        TextView textView =(TextView) findViewById(R.id.name);
        textView.setText(equipmentBean.getEquName());
        TextView equManufacturer =(TextView) findViewById(R.id.equManufacturer);
        editText = findViewById(R.id.e_node);
        equManufacturer.setText(equipmentBean.getEquManufacturer());
        button = findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EquipmentService equipmentService = new EquipmentServiceImpl();
                //equipmentBean.setEquNotes(editText.getText().toString());
                equipmentService.addEquipment(equipmentBean,editText.getText().toString(),SettingDeviceActivity.this);
            }
        });
    }

    public Handler handler=new Handler() {
        public void handleMessage (Message msg){
            switch (msg.what) {
                case 1:
                    Toast.makeText(SettingDeviceActivity.this, "添加设备成功", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SettingDeviceActivity.this, MainActivity.class));
                    break;
                case 2:

            }

        }
    };
}
