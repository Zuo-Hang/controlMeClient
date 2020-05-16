package com.example.myapplicationnumba.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;

/**
 * 登陆页面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    protected Button mBtnRegister;
private Button btnLogin;
    private EditText edtLoginUsername;

    private EditText edtLoginPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnRegister= (Button) this.findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
        edtLoginPwd=findViewById(R.id.edt_login_pwd);
        edtLoginPwd.setOnClickListener(this);
        btnLogin=findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.layout.activity_login:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login://点击了登陆按钮

        }
    }
}
