package com.example.myapplicationnumba.activitys.control;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.Server.EquipmentService;
import com.example.myapplicationnumba.Server.impl.EquipmentServiceImpl;
import com.example.myapplicationnumba.activitys.find.SettingDeviceActivity;
import com.example.myapplicationnumba.entity.EquipmentBean;

/**
 * 设备信息显示页面
 */
public class DeviceInformationActivity extends AppCompatActivity implements View.OnClickListener  {

    private TextView equName;
    private TextView equManufacturer;
    private TextView equTypes;
    private TextView equStatus;
    private TextView equNotes;
    private Button managementEquipment;
    private Button deleteEquipment;
    private Button update;
    private EquipmentService equipmentService;
    private EquipmentBean equipmentBean;

    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        equipmentBean = (EquipmentBean) bundle.get("data");
        System.out.println(equipmentBean);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_information_layout);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        equName = findViewById(R.id.equ_name);
        equName.setText(equipmentBean.getEquName());
        equManufacturer = findViewById(R.id.equ_manufacturer);
        equManufacturer.setText(equipmentBean.getEquManufacturer());
        equTypes = findViewById(R.id.equ_types);
        equTypes.setText(equipmentBean.getEquTypes().toString());
        equStatus = findViewById(R.id.equ_status);
        equStatus.setText(equipmentBean.getEquStatus().toString());
        equNotes = findViewById(R.id.equ_notes);
        update = findViewById(R.id.update_equipment);
        update.setOnClickListener(this);
//        if(equipmentBean.getEquNotes()!=null){
//
//        }
        managementEquipment = findViewById(R.id.management_equipment);
        //管理button事件绑定
        managementEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceInformationActivity.this, ManagementEquipmentActivity.class);
                intent.putExtra("data",equipmentBean);
                startActivity(intent);
            }
        });
        deleteEquipment = findViewById(R.id.delete_equipment);
        //删除button事件绑定
        deleteEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equipmentService = new EquipmentServiceImpl();
                equipmentService.moveOutMyEquipment(equipmentBean.getEquId(), DeviceInformationActivity.this);
            }
        });
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //当为1时，代表查询成功。更改信息
                    Toast.makeText(DeviceInformationActivity.this, "设备已删除", Toast.LENGTH_LONG).show();
                    break;
                case 2:

            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_equipment://首页
                Toast.makeText(DeviceInformationActivity.this, "更新信息成功", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
