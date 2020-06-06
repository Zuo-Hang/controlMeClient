package com.example.myapplicationnumba.util;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 对项目中会出现的网络请求进行封装
 */
public class HttpUtil {
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Request.Builder builder = new Request.Builder();

    /**
     * 发送OkHttp POST 请求的方法
     * @param address  地址
     * @param formBodyBuild  post请求体
     * @return
     */
    public static Response sendOkHttpPostRequest(String address,FormBody.Builder formBodyBuild) {
        Request request = builder
                .url(address)
                .post(formBodyBuild.build()).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送Get请求
     * @param url
     * @return
     */
    public static Response sendOkHttpGetRequest(String url) {
        Request request = builder.url(url).build();
        Response response=null;
        try {
            response = okHttpClient.newCall(request).execute();
        }catch (Exception e){
        }
        return response;
    }

    /**
     * 向服务器发送请求（携带文件）
     * @param urlAddress
     * @param requestBody
     * @param callback
     */
    public static void sendMultipart(String urlAddress, RequestBody requestBody, Callback callback) {
        Request request = new Request.Builder().header("Authorization", "Client-ID " + "...").url(urlAddress).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}