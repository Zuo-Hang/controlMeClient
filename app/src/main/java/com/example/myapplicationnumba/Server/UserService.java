package com.example.myapplicationnumba.Server;

import com.example.myapplicationnumba.activitys.my.LoginActivity;

import java.io.File;

/**
 * 用户服务功能接口
 */
public interface UserService {
    /**
     * 获取当前登陆用户
     */
    void getUser();

    /**
     * 登陆功能
     * @param activity  环境上下文
     * @param username  用户名
     * @param password  密码
     */
    void login(LoginActivity activity, String username, String password);

    /**
     * 上传用户头像
     * @param file  所选择的图片文件
     */
    void uploadHeadShot(File file);
}
