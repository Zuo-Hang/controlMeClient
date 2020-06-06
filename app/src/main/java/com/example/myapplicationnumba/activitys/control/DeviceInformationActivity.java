package com.example.myapplicationnumba.activitys.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;

/**
 * 设备信息显示页面
 */
public class DeviceInformationActivity extends AppCompatActivity {

    private TextView equName;
    private TextView equManufacturer;
    private TextView equTypes;
    private TextView equStatus;
    private TextView equNotes;
    private Button managementEquipment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_information_layout);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        equName = findViewById(R.id.equ_name);
        equManufacturer = findViewById(R.id.equ_manufacturer);
        equTypes = findViewById(R.id.equ_types);
        equStatus = findViewById(R.id.equ_status);
        equNotes = findViewById(R.id.equ_notes);
        managementEquipment = findViewById(R.id.management_equipment);
        managementEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceInformationActivity.this, ManagementEquipmentActivity.class));
            }
        });
    }
}
