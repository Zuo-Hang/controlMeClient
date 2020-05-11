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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
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

import com.example.myapplicationnumba.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 从本地获取图片功能
 */
public class ChooseOrTakePictureActivity extends AppCompatActivity implements View.OnClickListener{
    private Uri imageUri;
    private ImageView imageView;
    private Button buttonChoose;
    private Button buttonTake;

    /**
     * 创建视图时
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_or_take_picture_activity);
        imageView = findViewById(R.id.imageView);
        buttonChoose = findViewById(R.id.button);
        buttonTake=findViewById(R.id.button2);
        registrationEvent();
    }

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