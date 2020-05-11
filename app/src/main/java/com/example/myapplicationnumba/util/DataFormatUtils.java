package com.example.myapplicationnumba.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapplicationnumba.entity_model.EquipmentModel;
import java.util.ArrayList;

/**
 * 数据格式变化的工具类
 */
public class DataFormatUtils {
    /**
     * json字符串-数组类型与JavaBean_List之间的转换
     * @param string  输入的json串
     */
    public static ArrayList<EquipmentModel> testJSONStrToJavaBeanList(String string){

        ArrayList<EquipmentModel> students = JSON.parseObject(string, new TypeReference<ArrayList<EquipmentModel>>() {});
        //ArrayList<Student> students1 = JSONArray.parseObject(JSON_ARRAY_STR, new TypeReference<ArrayList<Student>>() {});//因为JSONArray继承了JSON，所以这样也是可以的
        return students;
    }
}
