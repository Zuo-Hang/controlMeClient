package com.example.myapplicationnumba.activitys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.activitys.SettingDeviceActivity;

public class FindFragment extends Fragment {

    //定义寻找按钮
    protected Button mBtnFind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtnFind= (Button) getView().findViewById(R.id.btn_find);
        mBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //寻找新的设备
                Intent setDevice=new Intent(getActivity(), SettingDeviceActivity.class);
                startActivity(setDevice);
            }
        });
    }
}
