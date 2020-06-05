package com.example.myapplicationnumba.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.base.MyApplication;
import com.example.myapplicationnumba.entity_model.User;
import com.example.myapplicationnumba.util.HttpUtil;
import com.example.myapplicationnumba.util.LoginAndRegistrationUserUtil;
import com.example.myapplicationnumba.util.SaveUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * 登陆页面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    protected Button mBtnRegister;
    private EditText editUsername;
    private EditText editPassword;
    //服务器端的登录接口
    private String url = "http://192.168.43.198:8080/login";
    private String address = null;
    private String username;
    private String password;
    private FormBody.Builder formBodyBuild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取用户输入的数据
        mBtnRegister = (Button) this.findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
        editUsername = findViewById(R.id.edt_login_username);
        editPassword = findViewById(R.id.edt_login_pwd);
        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editUsername.getText().toString();
                password = editPassword.getText().toString();
                if (checkInput()) {//输入验证正确
                    //向服务器传参
                    formBodyBuild = new FormBody.Builder();
                    formBodyBuild.add("username",username);//此处添加所需要提交的参数
                    formBodyBuild.add("password",password);//此处添加所需要提交的参数
                    login();
                }
            }
        });
    }

    //登录操作中开启一个子线程
    private void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = HttpUtil.sendOkHttpPostRequest(url,formBodyBuild);
                    Headers headers = response.headers();
                    //获取到cookies
                    List<String> cookies=headers.values("Set-Cookie");
                    if (response != null) {
                        String responseData = response.body().string();
                        Map<String, Object> map = null;
                        //使用Gson解析服务器返回的数据
                        Gson gson = new Gson();
                        Type type = new TypeToken<Map<String, Object>>() {
                        }.getType();
                        map = gson.fromJson(responseData, type);
                        String msg = map.get("errorMsg").toString();
                        //判断登录成功还是失败，并向主线程传递数据
                        if ("成功".equals(msg)) {
                            Message message = new Message();
                            message.what = 1;
                            message.obj = msg;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            //new SaveUtil(getApplicationContext()).saveUserInformation(username, password);
                            //new SaveUtil(getApplicationContext()).SearchUserInformation();
                            MyApplication.saveUtil.saveUserInformation(username, password);
                            User user = MyApplication.saveUtil.SearchUserInformation();
                            System.out.println(user);
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.what = 2;
                            message.obj = msg;
                            handler.sendMessage(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //根据子线程传来数据的不同，进行不同的操作
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String data = (String) msg.obj;
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("msg", data);
                    startActivity(intent);
                    break;
                case 2:
                    String data2 = (String) msg.obj;
                    Toast.makeText(LoginActivity.this, data2, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //注册跳转
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    //输入检验
    public boolean checkInput() {
        String s = editUsername.getText().toString();
        String psw = editPassword.getText().toString();
        boolean b = LoginAndRegistrationUserUtil.checkTelephone(s);
        if (!b) {
            Toast.makeText(this, "输入的电话号码错误，请重新输入", Toast.LENGTH_SHORT).show();
            editUsername.setText("");
            editPassword.setText("");
            return false;
        }
        return true;
    }
}
