package com.example.myapplicationnumba.Server.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.example.myapplicationnumba.Server.TakeAPictureService;
import com.example.myapplicationnumba.activitys.my.ChooseOrTakePictureActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TakeAPictureServiceImpl implements TakeAPictureService {

    /**
     * 将拍摄的照片保存到本地
     * @param bitmap
     * @return
     */
    @Override
    public String saveBitmapToSD(Bitmap bitmap) {
        String path = Environment.getExternalStorageDirectory().getPath()+"/k";
        if(bitmap == null || path == null || path.length() == 0)
            return null;
        File file = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fos != null)
            {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //this.path=path;
        }
        return path;
    }

    /**
     * 将拍摄的照片进行采样
     * 原因：刚拍完照的照片直接拿到内存会特别大，避免内存溢出，先对图片做下采样处理，降低像素。
     * 事实证明刚拍完的照片上传确实会造成异常：FileSizeLimitExceededException: The field files exceeds its maximum permitted size of 1048576 bytes.
     * @param uri
     * @param destHeight
     * @param destWidth
     * @return
     */
    @Override
    public Bitmap getResizeBitmap(Uri uri, int destHeight, int destWidth, ChooseOrTakePictureActivity activity) {
        if(uri == null)
            return null;
        //第一次采样
        BitmapFactory.Options options = new BitmapFactory.Options();
        //该属性设置为true只会加载图片的边框进来，并不会加载图片具体的像素点
        options.inJustDecodeBounds = true;
        //第一次加载图片，这时只会加载图片的边框进来，并不会加载图片中的像素点
        try {
            BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri),null,options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //获得原图的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //定义缩放比例
        int sampleSize = 1;
        while (outHeight / sampleSize > destHeight || outWidth / sampleSize > destWidth) {
            //如果宽高的任意一方的缩放比例没有达到要求，都继续增大缩放比例
            //sampleSize应该为2的n次幂，如果给sampleSize设置的数字不是2的n次幂，那么系统会就近取值
            sampleSize *= 2;
        }
        /********************************************************************************************/
        //至此，第一次采样已经结束，我们已经成功的计算出了sampleSize的大小
        /********************************************************************************************/
        //二次采样开始
        //二次采样时我需要将图片加载出来显示，不能只加载图片的框架，因此inJustDecodeBounds属性要设置为false
        options.inJustDecodeBounds = false;
        //设置缩放比例
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //加载图片并返回
        try {
            return BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri),null,options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
