package com.example.myapplicationnumba.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.entity_model.EquipmentBean;

import java.util.List;

/**
 * 扫描到设备后的设置页面
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
    }
}
