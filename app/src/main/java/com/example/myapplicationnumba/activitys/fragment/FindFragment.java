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
import com.example.myapplicationnumba.activitys.find.ScanQRCodeActivity;
import com.example.myapplicationnumba.activitys.find.findActivity;
import com.example.myapplicationnumba.base.MyApplication;
import com.google.zxing.integration.android.IntentIntegrator;

public class FindFragment extends Fragment {
    //定义寻找按钮
    protected Button btnFind;
    //扫描二维码按钮
    protected Button scanningCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnFind = (Button) getView().findViewById(R.id.btn_find);
        MyApplication.destroyActivity("MainActivity");
        btnFind.setOnClickListener((view) -> {
            //寻找新的设备
            //MyApplication.addDestroyActivity(this.getActivity(), "MainActivity");
            Intent intent = new Intent(getActivity(), findActivity.class);
            startActivity(intent);
        });
        scanningCode = getView().findViewById(R.id.scanning_code);
        scanningCode.setOnClickListener((view) -> {
            new IntentIntegrator(getActivity()).setCaptureActivity(ScanQRCodeActivity.class).initiateScan();
        });
    }
}
