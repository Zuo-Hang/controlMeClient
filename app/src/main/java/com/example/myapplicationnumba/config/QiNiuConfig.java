package com.example.myapplicationnumba.config;

import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

public class QiNiuConfig {
    Configuration config = new Configuration.Builder()
            .connectTimeout(10)           // 链接超时。默认10秒
            .useHttps(true)               // 是否使用https上传域名
            .responseTimeout(60)          // 服务器响应超时。默认60秒
            .build();
    UploadManager uploadManager = new UploadManager(config, 3);

}
