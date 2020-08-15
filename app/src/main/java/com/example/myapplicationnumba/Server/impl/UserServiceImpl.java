package com.example.myapplicationnumba.Server.impl;

import android.app.Activity;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplicationnumba.Server.UserService;
import com.example.myapplicationnumba.activitys.base.MainActivity;
import com.example.myapplicationnumba.activitys.my.LoginActivity;
import com.example.myapplicationnumba.activitys.my.MyInformationActivity;
import com.example.myapplicationnumba.activitys.my.RegisterActivity;
import com.example.myapplicationnumba.base.MyApplication;
import com.example.myapplicationnumba.entity.SysUser;
import com.example.myapplicationnumba.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 用户服务功能实现类
 */
public class UserServiceImpl implements UserService {

    private String url = "http://192.168.43.198:8080/login";
    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     * 获取当前登陆用户
     */
    @Override
    public void getUser(MainActivity activity ) {
        String url = "http://192.168.43.198:8080/getUser?id=4";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String responseData = null;
                Response response = HttpUtil.sendOkHttpGetRequest(url);
                try {
                    responseData = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //将字符串转jsonObj
                JsonObject asJsonObject = new JsonParser().parse(responseData).getAsJsonObject();
                JsonElement data = asJsonObject.get("data");
                Gson gson = new Gson();

                    MyApplication.sysUser= gson.fromJson(data, SysUser.class);


                System.out.println(MyApplication.sysUser);
                Message message = new Message();
                message.what = 1;
                activity.handler.sendMessage(message);
            }
        }).start();
    }

    /**
     * 登陆功能
     * @param activity  环境上下文
     * @param username  用户名
     * @param password  密码
     */
    @Override
    public void login(LoginActivity activity, String username, String password) {
        FormBody.Builder formBodyBuild = new FormBody.Builder();
        formBodyBuild.add("username",username);//此处添加所需要提交的参数
        formBodyBuild.add("password",password);//此处添加所需要提交的参数
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
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity.getBaseContext(), "登录成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            MyApplication.saveUtil.saveUserInformation(username, password);

                            //User user = MyApplication.saveUtil.SearchUserInformation();
                            //System.out.println(user);
                            activity.handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.what = 2;
                            message.obj = msg;
                            activity.handler.sendMessage(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 上传用户头像
     * @param file  所选择的图片文件
     */
    @Override
    public void uploadHeadShot(File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接口地址
                String urlAddress = "http://192.168.43.198:8080/addheadshot";
                if (file != null && file.exists()) {
                    MultipartBody.Builder builder = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("files", "img" + "_" + System.currentTimeMillis() + ".jpg",
                                    RequestBody.create(MEDIA_TYPE_PNG, file))
                            .addFormDataPart("userId","2");
                    HttpUtil.sendMultipart(urlAddress, builder.build(), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            Log.e("---", "onResponse: 成功上传图片之后服务器的返回数据：" + result);
                            //result就是图片服务器返回的图片地址。
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 注册功能
     * @param userName 用户名
     * @param passWord 密码
     */
    @Override
    public void registerUser(RegisterActivity activity, String userName, String passWord) {
        FormBody.Builder formBodyBuild = new FormBody.Builder();
        formBodyBuild.add("userName",userName);//此处添加所需要提交的参数
        formBodyBuild.add("password",passWord);//此处添加所需要提交的参数
        String urlAddress = "http://192.168.43.198:8080/registerUser";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = HttpUtil.sendOkHttpPostRequest(urlAddress,formBodyBuild);
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
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity.getBaseContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            MyApplication.saveUtil.saveUserInformation(userName, passWord);

                            //User user = MyApplication.saveUtil.SearchUserInformation();
                            //System.out.println(user);
                            activity.handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.what = 1;
                            message.obj = msg;
                            activity.handler.sendMessage(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 更改用户信息
     * @param activity
     * @param user
     */
    @Override
    public void upDateUser(MyInformationActivity activity, SysUser user) {
        FormBody.Builder formBodyBuild = new FormBody.Builder();
        System.out.println(user);
        formBodyBuild.add("id",user.getId().toString());//此处添加所需要提交的参数
        formBodyBuild.add("userName",user.getUserName());//此处添加所需要提交的参数
        formBodyBuild.add("password",user.getPassword());//此处添加所需要提交的参数
        formBodyBuild.add("email",user.getEmail());//此处添加所需要提交的参数
        String urlAddress = "http://192.168.43.198:8080/updateuser";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = HttpUtil.sendOkHttpPostRequest(urlAddress,formBodyBuild);
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
                            activity.handler.sendMessage(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
