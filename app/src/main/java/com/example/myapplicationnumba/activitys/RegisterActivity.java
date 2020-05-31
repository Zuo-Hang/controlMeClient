package com.example.myapplicationnumba.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.util.LoginAndRegistrationUserUtil;
import com.example.myapplicationnumba.util.OkHttpUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 注册页面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView goBack;
    private EditText phoneNumber;
    private EditText code;
    private EditText password;
    private EditText passwordAgain;
    private Button btnSubmit;
    private TextView directAccess;
    private OkHttpUtils okHttpUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        goBack = this.findViewById(R.id.go_back);
        goBack.setOnClickListener(this);
        phoneNumber = this.findViewById(R.id.phone_number);
        code = this.findViewById(R.id.code);
        btnSubmit = this.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        directAccess=this.findViewById(R.id.direct_access);
        directAccess.setOnClickListener(this);
        password=this.findViewById(R.id.edit_password);
        passwordAgain=this.findViewById(R.id.edit_password_again);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_back://返回按钮
                this.finish();
                break;
            case R.id.btn_submit://注册按钮
                checkInput();
                break;
            case R.id.direct_access://定位到登陆界面
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    }

    private void checkInput() {
        String s = phoneNumber.getText().toString();
        String psw=password.getText().toString();
        String pswAg=passwordAgain.getText().toString();
        boolean b = LoginAndRegistrationUserUtil.checkTelephone(s);
        if (!b) {
            Toast.makeText(this, "输入的电话号码错误，请重新输入", Toast.LENGTH_SHORT).show();
            phoneNumber.setText("");
        }
        if(psw.equals(pswAg)){
            Toast.makeText(this, "密码相同", Toast.LENGTH_SHORT).show();
        }
//        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
//        OkHttpClient client = new OkHttpClient();
//        Request.Builder builder = new Request.Builder(JSON,josn);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        client.newCall(request).enqueue(callback);
        okHttpUtils=new OkHttpUtils();
        StringBuilder stringBuilder=new StringBuilder("http://10.100.52.107:8080/user/ins?");
        stringBuilder.append("phoneNumber="+s);
        stringBuilder.append("&password="+psw);
        okHttpUtils.response(stringBuilder.toString());
    }
}
