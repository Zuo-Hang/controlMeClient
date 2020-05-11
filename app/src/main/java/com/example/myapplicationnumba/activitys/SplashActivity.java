package com.example.myapplicationnumba.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.base.MyApplication;

/**
 * 闪屏页
 */
public class SplashActivity extends AppCompatActivity {

    protected Handler  mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏android系统的状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 隐藏应用程序的标题栏，即当前activity的标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        MyApplication.addDestroyActivity(this, "SplashActivity");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //从闪屏页跳到主界面
               startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        },2000);
    }
}
