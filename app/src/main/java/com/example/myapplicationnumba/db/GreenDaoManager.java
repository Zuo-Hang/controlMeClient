package com.example.myapplicationnumba.db;

import android.content.Context;

import com.example.myapplicationnumba.base.MyApplication;
import com.example.myapplicationnumba.entity_model.EquipmentModel;
import com.example.myapplicationnumba.entity_model.EquipmentModelDao;
import com.example.myapplicationnumba.util.DataFormatUtils;
import com.example.myapplicationnumba.util.FileUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 数据库功能类
 */
public class GreenDaoManager {

    private Context mContext;
    private EquipmentModelDao equipmentModelDao;

    /**
     * 构造器
     * @param context
     */
    public GreenDaoManager (Context context) {
        this.mContext = context;
//        获取DAO实例
        equipmentModelDao = MyApplication.mSession.getEquipmentModelDao();
    }

    /**
     * 根据json文件向数据库中插入数据
     */
    public void insertEquipments () {
        String json = FileUtil.getJson("goods.json", mContext);
//        如果不想因为重复添加数据而导致崩溃,可以使用insertOrReplaceInTx API
//        mGoodsModelDao.insertInTx(DataFormatUtils.getGoodsModels(json));
        equipmentModelDao.insertOrReplaceInTx(DataFormatUtils.testJSONStrToJavaBeanList(json));
    }

    /**
     * 查询所有并排序
     * @return
     */
    public List<EquipmentModel> queryEquipments () {
        QueryBuilder<EquipmentModel> result = equipmentModelDao.queryBuilder();
        result = result.orderAsc(EquipmentModelDao.Properties.Id);
        return result.list();
    }

}
