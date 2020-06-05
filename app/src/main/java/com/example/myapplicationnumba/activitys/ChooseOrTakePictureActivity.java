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

import java.io.File;
import java.io.FileNotFoundException;
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

    private File file;
    Uri uri;
    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private String path;

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

//——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————

    /**
     * 注册点击事件
     */
    private void registrationEvent() {
        buttonChoose.setOnClickListener(this);
        buttonTake.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
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
//————————————————————————————————————————————————————————————————————————————————
    /**
     * 网络上传图片
     * @param file
     */
    private void sendStudentInfoToServer( File file) {
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
//——————————————————————————————————————————————————————————————————————————————————————————————

    //安卓版本大于4.4的处理方法
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
            case R.id.button3:
                sendStudentInfoToServer(new File(path));
                break;
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

    /**
     * 打开相册逻辑
     * OK
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