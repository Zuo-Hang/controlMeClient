package com.example.myapplicationnumba.activitys.find;


import android.os.Bundle;

import com.example.myapplicationnumba.Server.FindService;
import com.example.myapplicationnumba.Server.impl.FindServiceImpl;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
/**
 * 利用广播发现周边设备的页面
 */
public class findActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        //向局域网发送广播
        FindService findService = new FindServiceImpl();
        findService.findByBroadcast(this);
    }

}