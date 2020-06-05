package com.example.myapplicationnumba.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.base.MyApplication;
import com.example.myapplicationnumba.entity_model.User;
import com.example.myapplicationnumba.util.SaveUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 闪屏页
 */
public class SplashActivity extends AppCompatActivity {
    protected Handler  mHandler=new Handler();
    private SaveUtil saveUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏android系统的状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 隐藏应用程序的标题栏，即当前activity的标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("启动了主界面");
        saveUtil = new SaveUtil(this);
        MyApplication.saveUtil=saveUtil;
        User user = saveUtil.SearchUserInformation();
        System.out.println(user);
        String phoneNumber = user.getPhoneNumber();
        String password = user.getPassword();
        MyApplication.addDestroyActivity(this, "SplashActivity");
        //如果本地没有登陆信息则跳转到登陆界面
//        if(password.equals("")||phoneNumber.equals("")){
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //从闪屏页跳到主界面
//                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                }
//            },2000);
//        }
//        else{
            //本地有登陆信息则跳转到主界面
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //从闪屏页跳到主界面
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            },2000);
        }
//    }
}
