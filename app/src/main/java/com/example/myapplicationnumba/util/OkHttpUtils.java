package com.example.myapplicationnumba.util;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 对项目中会出现的网络请求进行封装
 */
public class OkHttpUtils  {
    private final OkHttpClient mClient = new OkHttpClient();

    /**
     * 封装网络请求，应用场景。
     * 1.登陆、注册
     * 2.对于电器的控制请求
     * @param url
     */
    private void response(final String url) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                int code = response.code();
                Headers headers = response.headers();
                String content = response.body().string();
                final StringBuilder buf = new StringBuilder();
                buf.append("code: " + code);
                buf.append("\nHeaders: \n" + headers);
                buf.append("\nbody: \n" + content);
                //成功后做的一些事情
            }
        });
    }


    /**
     * 对于网络资源的获取请求
     *  1.查询个人信息
     *  2.刷新页面的请求
     * @param url
     */
    private void get(final String url) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                Request request = builder.build();
                Call call = mClient.newCall(request);
                Response response = null;
                try {
                    response = call.execute();
                    if (response.isSuccessful()) {
                        //做一些请求成功的事情
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        executor.shutdown();
    }

    /**
     * post请求：对于服务器端的上传请求，如上传头像至服务器
     * @param url
     * @param file
     */
    private void post(final String url, File file) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.post(RequestBody.create(MediaType.parse("image/png"),file));
        Request request = builder.build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String content = response.body().string();

                }
            }
        });
    }
}
