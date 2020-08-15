package com.example.myapplicationnumba.activitys.control;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.Server.EquipmentService;
import com.example.myapplicationnumba.Server.impl.EquipmentServiceImpl;
import com.example.myapplicationnumba.activitys.find.SettingDeviceActivity;
import com.example.myapplicationnumba.base.MyApplication;
import com.example.myapplicationnumba.entity.EquipmentBean;

import java.util.ArrayList;

/**
 * 设备控制页面
 */
public class ManagementEquipmentActivity extends AppCompatActivity {
    private EquipmentBean equipmentBean;
    EquipmentService equipmentService=new EquipmentServiceImpl();
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        equipmentBean = (EquipmentBean) bundle.get("data");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_equipment_layout);
        findViewById(R.id.on_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //向服务器发送控制请求
                equipmentService.control(equipmentBean.getEquId(),ManagementEquipmentActivity.this);
            }
        });
    }

    public Handler handler=new Handler() {
        public void handleMessage (Message msg){
            switch (msg.what) {
                case 1:

                    //当为1时，代表查询成功。更改信息
                    Toast.makeText(ManagementEquipmentActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
                    break;
                case 2:

            }

        }
    };
}
