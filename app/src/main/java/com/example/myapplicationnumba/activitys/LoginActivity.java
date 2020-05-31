package com.example.myapplicationnumba.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.util.HttpUtil;
import com.example.myapplicationnumba.util.LoginAndRegistrationUserUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
        mBtnRegister = (Button) this.findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
        edtLoginPwd = findViewById(R.id.edt_login_pwd);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        edtLoginUsername=findViewById(R.id.edt_login_username);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.layout.activity_login:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login://点击了登陆按钮
                if(checkInput()){//输入验证正确
                    loginWithOkHttp("ll",edtLoginUsername.getText().toString(),edtLoginPwd.getText().toString());
                }
        }
    }
    public boolean checkInput(){
        String s = edtLoginUsername.getText().toString();
        String psw=edtLoginPwd.getText().toString();
        boolean b = LoginAndRegistrationUserUtil.checkTelephone(s);
        if (!b) {
            Toast.makeText(this, "输入的电话号码错误，请重新输入", Toast.LENGTH_SHORT).show();
            edtLoginUsername.setText("");
            edtLoginPwd.setText("");
            return false;
        }
        return true;
    }

    //实现登录
    public void loginWithOkHttp(String address,String account,String password){
        HttpUtil.loginWithOkHttp(address,account,password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseData.equals("true")){
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
