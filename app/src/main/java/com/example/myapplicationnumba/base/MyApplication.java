package com.example.myapplicationnumba.base;

import android.app.Activity;
import android.app.Application;
import com.example.myapplicationnumba.entity.SysUser;
import com.example.myapplicationnumba.util.SaveUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * 全局应用 其中包含:
 * 2.销毁指定Activity
 */
public class MyApplication extends Application {

    private static Map<String, Activity> destroyMap = new HashMap<>();
    public static SaveUtil saveUtil;
    public static SysUser sysUser;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */
    public static void addDestroyActivity(Activity activity, String activityName) {
        destroyMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     */
    public static void destroyActivity(String activityName) {
        Set<String> keySet = destroyMap.keySet();
        if (keySet.size() > 0) {
            for (String key : keySet) {
                if (activityName.equals(key)) {
                    destroyMap.get(key).finish();
                }
            }
        }
    }
}
