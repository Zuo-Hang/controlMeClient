package com.example.myapplicationnumba.activitys;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.util.HttpUtil;
import com.example.myapplicationnumba.util.ImageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 从本地获取图片并上传至服务器功能
 */
public class ChooseOrTakePictureActivity extends AppCompatActivity implements View.OnClickListener{
    private Uri imageUri;
    private ImageView imageView;
    private Button buttonChoose;
    private Button buttonTake;

    private static final int REQUEST_CHOOSE_IMAGE = 0x01;
    private static final int REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT = 0xff;
    private TextView photoPath;
    private ImageView photo;
    String image_path;
    private File file;
    Uri uri;
    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     * 创建视图时
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_or_take_picture_activity);
        imageView = findViewById(R.id.imageView);
        glideLoadImage("https://android-me.oss-cn-beijing.aliyuncs.com/MyApplicationNumba/spbg.png");
        buttonChoose = findViewById(R.id.button);
        buttonTake=findViewById(R.id.button2);
        registrationEvent();
    }

    /**
     * 使用Glide加载网络图片
     * @param img 网络图片地址
     */
    private void glideLoadImage (String img) {
//      通过 RequestOptions 对象来设置Glide的配置
        RequestOptions options = new RequestOptions()
//                设置图片变换为圆角
                .circleCrop()
//                设置站位图
                .placeholder(R.mipmap.loading)
//                设置加载失败的错误图片
                .error(R.mipmap.loader_error);

//      Glide.with 会创建一个图片的实例，接收 Context、Activity、Fragment
        Glide.with(this)
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
    private void loadUrlImage (final String img) {
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
                    handler.sendMessage(message);
                }
            }
        }.start();
    }


    /**
     *
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
//                当获取到成功的状态码时执行
                case 200:
//                    获取携带的bitmap
                    Bitmap bitmap = (Bitmap) msg.obj;
//                    imageView展示
                    imageView.setImageBitmap(bitmap);
                    break;
//                 当请求失败获取出现异常的时候回调
                default:
//                    展示加载失败的图片
                    imageView.setImageResource(R.mipmap.loader_error);
//                    打印失败的状态码
                    Toast.makeText(ChooseOrTakePictureActivity.this, "code: " + msg.what , Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    /**
     * 注册点击事件
     */
    private void registrationEvent() {
        buttonChoose.setOnClickListener(this);
        buttonTake.setOnClickListener(this);
    }


    /**
     * 获取权限的结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openAlbum();
            else Toast.makeText(ChooseOrTakePictureActivity.this, "你拒绝了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 启动相册的方法
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK)
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
        }
        if (requestCode == 2) {
            //判断安卓版本
            if (resultCode == RESULT_OK && data != null) {
                if (Build.VERSION.SDK_INT >= 19)
                    handImage(data);
                else handImageLow(data);
            }
        }
        if (requestCode == REQUEST_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            //获取图片的uri
            uri= data.getData();
            //图片的绝对路径
            image_path = ImageUtils.getRealFilePath(this, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(image_path);
            photo.setImageBitmap(bitmap);
            file = new File(image_path);
            //选取完图片后调用上传方法，将图片路径放入参数中
            sendStudentInfoToServer(file);
        }
    }

    /**
     * 网络上传图片
     * @param file
     */
    private void sendStudentInfoToServer( File file) {
        //接口地址
        String urlAddress = "http://116.62.130.5:80/sjb/portal/login/img";
        if (file != null && file.exists()) {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("files", "img" + "_" + System.currentTimeMillis() + ".jpg",
                            RequestBody.create(MEDIA_TYPE_PNG, file));
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


    //安卓版本大于4.4的处理方法
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handImage(Intent data) {
        String path = null;
        Uri uri = data.getData();
        //根据不同的uri进行不同的解析
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                path = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            path = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }
        //展示图片
        displayImage(path);
    }


    //安卓小于4.4的处理方法
    private void handImageLow(Intent data) {
        Uri uri = data.getData();
        String path = getImagePath(uri, null);
        displayImage(path);
    }

    //content类型的uri获取图片路径的方法
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 根据路径展示图片的方法
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            //使用Bitmap可以展示图片
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "fail to set image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.button://选择按钮
                choose();
                break;
            case  R.id.button2://拍照按钮
                take();
                break;
        }

    }

    /**
     * 拍照逻辑
     */
    private void take() {
        //创建一个File对象。getExternalCacheDir()获取应用的缓存目录，outputImage.jpg是照片名称
        File outputImage = new File(getExternalCacheDir(), "outputImage.jpg");
        try {
            //创建一个空文件
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //不同的安卓版本对用不同的获取Uri的方法
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(ChooseOrTakePictureActivity.this, "huan", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机的对应Activity
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 1);
    }

    /**
     * 打开相册逻辑
     */
    private void choose() {
        //动态申请权限
        if (ContextCompat.checkSelfPermission(ChooseOrTakePictureActivity.this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChooseOrTakePictureActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            //执行启动相册的方法
            openAlbum();
        }
    }


}