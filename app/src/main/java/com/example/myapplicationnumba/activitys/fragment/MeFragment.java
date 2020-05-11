package com.example.myapplicationnumba.activitys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplicationnumba.activitys.LoginActivity;
import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.activitys.MyInformationActivity;

public class MeFragment extends Fragment implements View.OnClickListener{

    //定义登陆按钮
    protected Button mBtnLogin;
    private RelativeLayout myInfoTirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtnLogin= (Button) getView().findViewById(R.id.btn_login);
        myInfoTirm = (RelativeLayout)getView().findViewById(R.id.my_info_tirm);
        myInfoTirm.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btn_login://登陆按钮
                Intent login=new Intent(getActivity(), LoginActivity.class);
                startActivity(login);
                break;
            case  R.id.my_info_tirm://我的信息
                Intent myInformation=new Intent(getActivity(), MyInformationActivity.class);
                startActivity(myInformation);
                break;
        }
    }
}
