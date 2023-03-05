package me.codeleep.jsondiff.test.dataFactory;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.model.JsonComparedOption;
import me.codeleep.jsondiff.test.model.MetaData;
import me.codeleep.jsondiff.test.utils.ResourceUtils;

import java.util.*;


/**
 * @author: chenfeng
 * @createTime: 2023/3/2 21:55
 * @description: 工厂工具类
 */
public class FactoryUtil {
    /**
     * 加载数据
     * @param path 加载路径
     * @param loadDataName 加载用例类型名称数组
     * @param maxMap 存储每种类型用例的个数
     * @param arrayData 存储用例
     */
    public static void load(String path,String[] loadDataName,Map<String, Integer> maxMap,Map<String, ArrayList<MetaData>> arrayData) {
        String expectContent = ResourceUtils.loadResourceLine(path);
        JSONObject jsonObject = (JSONObject) JSON.parse(expectContent);
        for (String name : loadDataName) {
            arrayData.put(name, JsonArrayToMetadataList(jsonObject.getJSONArray(name), name,maxMap));
        }
    }

    /**
     * 将JsonArray对象转换为List<Metadata> 并将每种类型的个数进行统计赋值
     * @param jsonArray 需要转换的对象
     * @param type      他是什么类型
     * @param maxMap 存储每种类型用例的个数
     * @return ArrayList<MetaData>
     */
    private static ArrayList<MetaData> JsonArrayToMetadataList(JSONArray jsonArray, String type,Map<String, Integer> maxMap) {
        ArrayList<MetaData> list = new ArrayList<>();
        int count = 0;
        for (Object i : jsonArray) {
            JSONObject jsonObject = (JSONObject) i;
            MetaData metaData = new MetaData(jsonObject.getString("caseName"),jsonObject.get("expect"),
                    jsonObject.get("actual"),
                    jsonObject.get("ret"),
                    getOptionObject((JSONObject) jsonObject.get("option")));
            list.add(metaData);
            count++;
        }

        maxMap.put(type,count);
        return list;
    }

    /**
     * 将JsonObject对象转换成配置对象JsonComparedOption
     *
     * @param jsonObject JsonObject对象
     * @return JsonComparedOption
     */
    private static JsonComparedOption getOptionObject(JSONObject jsonObject) {
        if (jsonObject == null)
            return null;
        JsonComparedOption jsonComparedOption = new JsonComparedOption();
        boolean ignoreOrder = false;
        try{
            ignoreOrder = jsonObject.getBoolean("ignoreOrder") != null && jsonObject.getBoolean("ignoreOrder");
        }catch (ExceptionInInitializerError e){
            e.printStackTrace();
        }
        jsonComparedOption.setIgnoreOrder(ignoreOrder);
        String mapping = jsonObject.getString("mapping");
        if(mapping != null && !mapping.isEmpty()){
            jsonComparedOption.setMapping((HashMap<String,String>)JSONObject.parseObject(mapping,Map.class) );
        }
        jsonComparedOption.setIgnorePath(ArrStringToSet(jsonObject.getString("ignorePath")));
        jsonComparedOption.setIgnoreKey(ArrStringToSet(jsonObject.getString("ignoreKey")));
        return jsonComparedOption;
    }

    /**
     * 将从json中获取到的array字符串转化为set类型
     * @param stringList json array字符串
     * @return HashSet 转化后的set对象
     */
     private static HashSet<String> ArrStringToSet(String stringList){
         if (stringList == null || stringList.isEmpty()){
             return null;
         }
         List<String> arrayList = JSON.parseArray(stringList,String.class);
       return new HashSet<>(arrayList);
     }

}
