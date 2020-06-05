package com.example.myapplicationnumba.activitys.my;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.base.MyApplication;
import com.example.myapplicationnumba.util.ZXingUtils;
import com.google.gson.Gson;


/**
 * 我的二维码页面
 */
public class MyQrCodeActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_qr_code_layout);
        if( MyApplication.sysUser!=null){
            ImageView iv_qr_code = (ImageView)findViewById(R.id.iv_qr_code);
            String content=new Gson().toJson(MyApplication.sysUser); //二维码内容
            Bitmap bitmap = ZXingUtils.createQRImage(content, 250, 250);
            iv_qr_code.setImageBitmap(bitmap);
        }else{
            Toast.makeText(MyQrCodeActivity.this, "您还未登录！", Toast.LENGTH_SHORT).show();
        }
    }
}
