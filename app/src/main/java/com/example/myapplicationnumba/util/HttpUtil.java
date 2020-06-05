package com.example.myapplicationnumba.util;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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


    public static void sendMultipart(String urlAddress, RequestBody requestBody, Callback callback) {
//            这里根据需求传，不需要可以注释掉
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("title", "wangshu")
//                .addFormDataPart("image", "wangshu.jpg",
//                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
//                .build();
//        private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        Request request = new Request.Builder().header("Authorization", "Client-ID " + "...").url(urlAddress).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void loginWithOkHttp(String address, String account, String password, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("loginAccount",account)
                .add("loginPassword",password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static Response sendOkHttpRequest(String address) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }









}