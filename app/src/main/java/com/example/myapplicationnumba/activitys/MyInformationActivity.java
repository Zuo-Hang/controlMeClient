package com.example.myapplicationnumba.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplicationnumba.R;

public class MyInformationActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView headShot;
    private Button setBut;
    private ImageView returnBut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information);
        initView();
    }

    public void initView(){
        headShot= (ImageView)this.findViewById(R.id.head_shot);
        setBut= (Button)this.findViewById(R.id.set_but);
        returnBut= (ImageView)this.findViewById(R.id.return_but);
        headShot.setOnClickListener(this);
        setBut.setOnClickListener(this);
        returnBut.setOnClickListener(this);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.head_shot://头像
                Intent login=new Intent(this, ChooseOrTakePictureActivity.class);
                startActivity(login);
                break;
            case  R.id.menu_find://发现
                break;
            case  R.id.menu_me://我的
                break;
        }
    }
}
