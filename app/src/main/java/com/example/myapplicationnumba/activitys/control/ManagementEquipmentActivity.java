package com.example.myapplicationnumba.activitys.control;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;

/**
 * 设备控制页面
 */
public class ManagementEquipmentActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_equipment_layout);
        findViewById(R.id.on_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //向服务器发送控制请求

            }
        });
    }
}
