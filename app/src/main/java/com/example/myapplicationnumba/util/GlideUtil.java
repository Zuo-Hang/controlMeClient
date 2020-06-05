package com.example.myapplicationnumba.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplicationnumba.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GlideUtil {
    /**
     * 使用Glide加载网络图片
     * @param img 网络图片地址
     */
    public void glideLoadImage (String img, Activity activity, ImageView imageView) {
//      通过 RequestOptions 对象来设置Glide的配置
        RequestOptions options = new RequestOptions()
//                设置图片变换为圆角
                .circleCrop()
//                设置站位图
                .placeholder(R.mipmap.loading)
//                设置加载失败的错误图片
                .error(R.mipmap.loader_error);

//      Glide.with 会创建一个图片的实例，接收 Context、Activity、Fragment
        Glide.with(activity)
//                指定需要加载的图片资源，接收 Drawable对象、网络图片地址、本地图片文件、资源文件、二进制流、Uri对象等等
                .load(img)
//                指定配置
                .apply(options)
//                用于展示图片的ImageView
                .into(imageView);
    }


    /**
     * 加载网络图片
     * @param img 网络图片地址
     */
    public void loadUrlImage (final String img) {
//        开启子线程，用于进行网络请求
        new Thread(){
            @Override
            public void run() {
//                创建消息对象，用于通知handler
                Message message = new Message();
                try {
//                    根据传入的路径生成对应的URL
                    URL url = new URL(img);
//                    创建连接
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    设置请求方式为GET
                    httpURLConnection.setRequestMethod("GET");
//                    获取返回码
                    int code = httpURLConnection.getResponseCode();
//                    当返回码为200时，表示请求成功
                    if (code == 200) {
//                        获取数据流
                        InputStream inputStream = httpURLConnection.getInputStream();
//                        利用位图工程根据数据流生成对应的位图对象
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                        利用message对象将生成的bitmap携带到handler
                        message.obj = bitmap;
//                        成功的状态码
                        message.what = 200;
                    } else {
//                        失败的状态码
                        message.what = code;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    当出现异常的时候，状态码设置为 -1
                    message.what = -1;
                } finally {
//                    通知handler
                   // handler.sendMessage(message);
                }
            }
        }.start();
    }
//    /**
//     *
//     */
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg,Activity activity,ImageView imageView) {
//            super.handleMessage(msg);
//            switch (msg.what) {
////                当获取到成功的状态码时执行
//                case 200:
////                    获取携带的bitmap
//                    Bitmap bitmap = (Bitmap) msg.obj;
////                    imageView展示
//                    imageView.setImageBitmap(bitmap);
//                    break;
////                 当请求失败获取出现异常的时候回调
//                default:
////                    展示加载失败的图片
//                    imageView.setImageResource(R.mipmap.loader_error);
////                    打印失败的状态码
//                    Toast.makeText(activity, "code: " + msg.what , Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };
}
