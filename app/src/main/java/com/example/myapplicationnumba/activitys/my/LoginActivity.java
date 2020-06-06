package com.example.myapplicationnumba.activitys.my;

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
import com.example.myapplicationnumba.Server.UserService;
import com.example.myapplicationnumba.Server.impl.UserServiceImpl;
import com.example.myapplicationnumba.activitys.base.MainActivity;
import com.example.myapplicationnumba.util.LoginAndRegistrationUserUtil;

/**
 * 登陆页面
 */
public class LoginActivity extends AppCompatActivity {
    protected Button mBtnRegister;
    private EditText editUsername;
    private EditText editPassword;
    private String username;
    private String password;
    //根据子线程传来数据的不同，进行不同的操作
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //当为1时，代表登陆成功。跳转到主页面。
                    String data = (String) msg.obj;
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("msg", data);
                    startActivity(intent);
                    break;
                case 2:
                    //当为2时，登陆失败。输出失败信息。
                    String data2 = (String) msg.obj;
                    Toast.makeText(LoginActivity.this, data2, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 创建视图
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局文件
        setContentView(R.layout.activity_login);
        //获取用户输入的数据
        mBtnRegister = (Button) this.findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        editUsername = findViewById(R.id.edt_login_username);
        editPassword = findViewById(R.id.edt_login_pwd);
        Button login = findViewById(R.id.btn_login);
        //为登陆按钮绑定事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editUsername.getText().toString();
                password = editPassword.getText().toString();
                if (checkInput()) {//输入验证正确
                    //调用登陆服务，登录操作中开启一个子线程。向服务器传参
                    UserService userService = new UserServiceImpl();
                    userService.login(LoginActivity.this,username,password);
                }
            }
        });
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
