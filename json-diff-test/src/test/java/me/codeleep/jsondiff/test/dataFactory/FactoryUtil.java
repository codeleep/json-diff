package me.codeleep.jsondiff.test.dataFactory;

import me.codeleep.jsondiff.facade.DiffJsonFactory;
import me.codeleep.jsondiff.model.DIFFJSON;
import me.codeleep.jsondiff.model.JsonComparedOption;
import me.codeleep.jsondiff.model.MetaData;
import me.codeleep.jsondiff.spi.array.DiffJsonArray;
import me.codeleep.jsondiff.spi.object.DiffJsonObject;
import me.codeleep.jsondiff.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.Map;


/**
 * @author: fen
 * @createTime: 2022/08/17 23:20
 * @description: 工厂工具类
 */
public class FactoryUtil {
    /**
     * 加载数据
     * @param path 加载路径
     * @param loadDataName 加载用例类型名称数组
     * @param maxMap 存储每种类型用例的个数
     * @param arrayData 存储用例
     * @return
     */
    public static void load(String path,String[] loadDataName,Map<String, Integer> maxMap,Map<String, ArrayList<MetaData>> arrayData) {
        String expectContent = ResourceUtils.loadResourceLine(path);
        DiffJsonObject jsonObject = (DiffJsonObject) DIFFJSON.parse(expectContent);
        for (String name : loadDataName) {
            arrayData.put(name, JsonArrayToMetadataList(jsonObject.getDiffJsonArray(name), name,maxMap));
        }
    }

    /**
     * 将JsonArray对象转换为List<Metadata> 并将每种类型的个数进行统计赋值
     *
     * @param jsonArray 需要转换的对象
     * @param type      他是什么类型
     * @param maxMap 存储每种类型用例的个数
     * @return
     */
    private static ArrayList<MetaData> JsonArrayToMetadataList(DiffJsonArray jsonArray, String type, Map maxMap) {
        ArrayList<MetaData> list = new ArrayList<>();
        int count = 0;
        for (Object i : jsonArray.toArray()) {
            DiffJsonObject jsonObject = (DiffJsonObject) i;
            MetaData metaData = new MetaData(jsonObject.get("expect"),
                    jsonObject.get("actual"),
                    jsonObject.get("ret"),
                    getOptionObject((DiffJsonObject) jsonObject.get("option")));
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
    private static JsonComparedOption getOptionObject(DiffJsonObject jsonObject) {
        if (jsonObject == null)
            return null;
        JsonComparedOption jsonComparedOption = new JsonComparedOption();
        boolean ignoreOrder = false;
        try{
            ignoreOrder =  jsonObject.getBoolean("ignoreOrder") == null?false:jsonObject.getBoolean("ignoreOrder");
        }catch (ExceptionInInitializerError e){
            e.printStackTrace();
        }
        jsonComparedOption.setIgnoreOrder(ignoreOrder);
        jsonComparedOption.setMapping((Map) jsonObject.get("mapping"));
        jsonComparedOption.setIgnorePath(DIFFJSON.parseArray(jsonObject.getString("ignorePath"), String.class));
        jsonComparedOption.setIgnoreKey(DIFFJSON.parseArray(jsonObject.getString("ignoreKey"), String.class));
        return jsonComparedOption;
    }
}
