package com.example.myapplicationnumba.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplicationnumba.entity.SysUser;

/**
 * 保存登陆信息工具类
 */
public class SaveUtil {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SaveUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //获取编辑器
        editor = sharedPreferences.edit();
    }

    public boolean saveUserInformation(String username,String password) {

        editor.putString("account", username);
        editor.putString("password", password);
        boolean commit = editor.commit();//提交修改
        return commit;
    }

    public SysUser SearchUserInformation() {
        String account = sharedPreferences.getString("account", "");
        String password = sharedPreferences.getString("password", "");
        System.out.println(account);
        System.out.println(password);
        SysUser sysUser = new SysUser();
        sysUser.setPhoneNumber(account);
        sysUser.setPassword(password);
        return sysUser;
    }
}
