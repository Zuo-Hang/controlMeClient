package com.example.myapplicationnumba.Server;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.myapplicationnumba.activitys.my.ChooseOrTakePictureActivity;

public interface TakeAPictureService {
    /**
     * 将拍摄的照片保存到本地
     * @param bitmap
     * @return
     */
    String saveBitmapToSD(Bitmap bitmap);

    /**
     * 将拍摄的照片进行采样
     * 原因：刚拍完照的照片直接拿到内存会特别大，避免内存溢出，先对图片做下采样处理，降低像素。
     * 事实证明刚拍完的照片上传确实会造成异常：FileSizeLimitExceededException: The field files exceeds its maximum permitted size of 1048576 bytes.
     * @param uri
     * @param destHeight
     * @param destWidth
     * @return
     */
    Bitmap getResizeBitmap(Uri uri, int destHeight, int destWidth, ChooseOrTakePictureActivity activity);
}
