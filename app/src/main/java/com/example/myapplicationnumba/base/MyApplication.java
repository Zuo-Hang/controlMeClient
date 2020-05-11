package com.example.myapplicationnumba.base;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


import com.example.myapplicationnumba.entity_model.DaoMaster;
import com.example.myapplicationnumba.entity_model.DaoSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 全局应用 其中包含:
 * 1.数据库的创建和会话的管理
 * 2.销毁指定Activity
 */
public class MyApplication extends Application {

    public static DaoSession mSession;
    private static Map<String, Activity> destroyMap = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initDb();
    }

    /**
     * 初始化数据库
     */
    public void initDb () {
//        获取SQLiteOpenHelper对象devOpenHelper
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "equipment.db");
//        获取SQLiteDatabase
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
//        加密数据库
//        Database db = devOpenHelper.getEncryptedWritableDb("123");
//        创建DaoMaster实例
//        DaoMaster保存数据库对象（SQLiteDatabase）并管理特定模式的Dao类（而不是对象）。
//        它具有静态方法来创建表或将它们删除。
//        其内部类OpenHelper和DevOpenHelper是在SQLite数据库中创建模式的SQLiteOpenHelper实现。
        DaoMaster daoMaster = new DaoMaster(db);
//        管理特定模式的所有可用Dao对象
        mSession = daoMaster.newSession();
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
