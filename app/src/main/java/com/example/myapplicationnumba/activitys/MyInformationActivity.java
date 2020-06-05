package com.example.myapplicationnumba.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.base.MyApplication;

public class MyInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView headShot;
    private Button setBut;
    private ImageView returnBut;
    private TextView nicknames;
    private ImageView sex;
    private TextView accountNumber;
    private TextView email;
    private TextView phoneNumber;
    private String url="https://android-me.oss-cn-beijing.aliyuncs.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information);
        initView();
    }

    public void initView() {
        headShot = (ImageView) this.findViewById(R.id.head_shot);
        setBut = (Button) this.findViewById(R.id.set_but);
        returnBut = (ImageView) this.findViewById(R.id.return_but);
        nicknames = (TextView) this.findViewById(R.id.nicknames);
        sex = (ImageView) this.findViewById(R.id.sex);
        accountNumber = (TextView) this.findViewById(R.id.account_number);
        email = (TextView) findViewById(R.id.email);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        headShot.setOnClickListener(this);
        setBut.setOnClickListener(this);
        returnBut.setOnClickListener(this);
        //加载头像
        url =url +MyApplication.sysUser.getHeadShot();
        glideLoadImage(url);
        //加载昵称
        nicknames.setText(MyApplication.sysUser.getUserName());
        //加载性别
        String sexs = MyApplication.sysUser.getSex();
        int res = R.mipmap.male;
        if (sexs.equals("女")) {
            res = R.mipmap.female;
        }
        Glide.with(this)
                .load(res)
                .into(sex);
        //加载账号
        accountNumber.setText(MyApplication.sysUser.getAccount());
        //加载手机号
        phoneNumber.setText(MyApplication.sysUser.getPhoneNumber());
        //加载邮箱
        email.setText(MyApplication.sysUser.getEmail());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_shot://头像
                Intent login = new Intent(this, ChooseOrTakePictureActivity.class);
//                Intent login=new Intent(this, ChooseOrTakePictureActivity.class);
                startActivity(login);
                break;
            case R.id.menu_find://发现
                break;
            case R.id.menu_me://我的
                break;
        }
    }


    private void glideLoadImage(String img) {
//      通过 RequestOptions 对象来设置Glide的配置
        RequestOptions options = new RequestOptions()
//                设置图片变换为圆角
                .circleCrop()
//                设置站位图
                .placeholder(R.mipmap.loading)
//                设置加载失败的错误图片
                .error(R.mipmap.loader_error);

//      Glide.with 会创建一个图片的实例，接收 Context、Activity、Fragment
        Glide.with(this)
//                指定需要加载的图片资源，接收 Drawable对象、网络图片地址、本地图片文件、资源文件、二进制流、Uri对象等等
                .load(img)
//                指定配置
                .apply(options)
//                用于展示图片的ImageView
                .into(headShot);
    }
}
