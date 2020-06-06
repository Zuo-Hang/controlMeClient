package com.example.myapplicationnumba.activitys.my;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.myapplicationnumba.Server.TakeAPictureService;
import com.example.myapplicationnumba.Server.impl.TakeAPictureServiceImpl;
import com.example.myapplicationnumba.Server.impl.UserServiceImpl;
import com.example.myapplicationnumba.util.HttpUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    private Button buttonSave;
    private String path;
    private TakeAPictureService takeAPictureService;

    /**
     * 创建视图时
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_or_take_picture_activity);
        imageView = findViewById(R.id.imageView);
        glideLoadImage("https://android-me.oss-cn-beijing.aliyuncs.com/2_img_1591187738571.jpg");
        buttonChoose = findViewById(R.id.button);
        buttonTake=findViewById(R.id.button2);
        buttonSave=findViewById(R.id.button3);
        registrationEvent();
    }
    /**
     * 注册点击事件
     */
    private void registrationEvent() {
        buttonChoose.setOnClickListener(this);
        buttonTake.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
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
            case R.id.button3:
                new UserServiceImpl().uploadHeadShot(new File(path));
                break;
        }

    }


    /**
     * 使用Glide加载网络图片
     * @param img 网络图片地址
     */
    private void glideLoadImage (String img) {
//      通过 RequestOptions 对象来设置Glide的配置
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loader_error);

        Glide.with(this)
                .load(img)
                .apply(options)
                .into(imageView);
    }

//——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
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
     * 打开相册逻辑（判断权限）
     * OK
     */
    private void choose() {
        //判断软件是否具备权限，若不具备则动态申请
        if (ContextCompat.checkSelfPermission(ChooseOrTakePictureActivity.this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChooseOrTakePictureActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            //具备则执行启动相册的方法
            openAlbum();
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

    /**
     * 当从其他视图返回时调用
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //当为1时为拍照逻辑
        if (requestCode == 1) {
            if (resultCode == RESULT_OK)
                try {
                    takeAPictureService = new TakeAPictureServiceImpl();
                    //采样
                    Bitmap bitmap = takeAPictureService.getResizeBitmap(imageUri, 2000, 2000, this);
                    //保存图片到本地
                    path = takeAPictureService.saveBitmapToSD(bitmap);
                    //更改视图
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        //当为2的时候为相册逻辑
        if (requestCode == 2) {
            //判断安卓版本
            if (resultCode == RESULT_OK && data != null) {
                if (Build.VERSION.SDK_INT >= 19)
                    handImage(data);
                else handImageLow(data);
            }
        }
    }

    /**
     * 安卓版本大于4.4的处理方法
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handImage(Intent data) {
        path = null;
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

    /**
     * 安卓小于4.4的处理方法
     * @param data
     */
    private void handImageLow(Intent data) {
        Uri uri = data.getData();
        String path = getImagePath(uri, null);
        displayImage(path);
    }

    /**
     * content类型的uri获取图片路径的方法
     * @param uri
     * @param selection
     * @return
     */
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
     * 拍照逻辑
     */
    private void take() {
        //创建一个File对象。getExternalCacheDir()获取应用的缓存目录，outputImage.jpg是照片名称
        File outputImage = new File(getExternalCacheDir(),"outputImage.jpg");
        try{
            //创建一个空文件
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        //不同的安卓版本对用不同的获取Uri的方法
        if (Build.VERSION.SDK_INT>=24){
            imageUri =FileProvider.getUriForFile(this,"huan",outputImage);
        }else{
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机的对应Activity
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,1);
    }
}