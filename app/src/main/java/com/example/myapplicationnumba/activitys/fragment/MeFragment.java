package com.example.myapplicationnumba.activitys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplicationnumba.activitys.my.LoginActivity;
import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.activitys.my.MyInformationActivity;
import com.example.myapplicationnumba.activitys.my.ContactTheDeveloperActivity;
import com.example.myapplicationnumba.activitys.my.MyQrCodeActivity;
import com.example.myapplicationnumba.activitys.my.SecurityCenterActivity;
import com.example.myapplicationnumba.activitys.my.SettingsActivity;
import com.example.myapplicationnumba.activitys.my.TipsActivity;
import com.example.myapplicationnumba.base.MyApplication;

public class MeFragment extends Fragment implements View.OnClickListener {

    //定义登陆按钮
    protected ImageView mBtnLogin;
    private LinearLayout myInfoTirm;
    private String url = "https://android-me.oss-cn-beijing.aliyuncs.com/2_img_1591187738571.jpg";
    public TextView myNickNames;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        glideLoadImage(url);
        myNickNames = getView().findViewById(R.id.my_nick_names);
    }

    /**
     * 初始化视图，并绑定事件
     */
    public void initView() {
        mBtnLogin = (ImageView) getView().findViewById(R.id.btn_login);
        myInfoTirm = getView().findViewById(R.id.my_info_tirm);
        getView().findViewById(R.id.tips).setOnClickListener(this);
        getView().findViewById(R.id.security_center).setOnClickListener(this);
        getView().findViewById(R.id.my_qr_code).setOnClickListener(this);
        getView().findViewById(R.id.contact_the_developer).setOnClickListener(this);
        getView().findViewById(R.id.settings).setOnClickListener(this);
        myInfoTirm.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登陆按钮
                Intent login = new Intent(getActivity(), LoginActivity.class);
                startActivity(login);
                break;
            case R.id.my_info_tirm://我的信息
                Intent myInformation = new Intent(getActivity(), MyInformationActivity.class);
                startActivity(myInformation);
                break;
            case R.id.tips://我的锦囊
                startActivity(new Intent(getActivity(), TipsActivity.class));
                break;
            case R.id.security_center://安全中心
                startActivity(new Intent(getActivity(), SecurityCenterActivity.class));
                break;
            case R.id.my_qr_code://我的二维码
                startActivity(new Intent(getActivity(), MyQrCodeActivity.class));
                break;
            case R.id.contact_the_developer://反馈意见
                startActivity(new Intent(getActivity(), ContactTheDeveloperActivity.class));
                break;
            case R.id.settings://设置
                startActivity(new Intent(getActivity(), SettingsActivity.class));
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
                .into(mBtnLogin);
    }
}
