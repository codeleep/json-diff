package me.codeleep.jsondiff.test.dataFactory;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.model.JsonComparedOption;
import me.codeleep.jsondiff.model.MetaData;
import me.codeleep.jsondiff.utils.ResourceUtils;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据工厂，用于初始化数据
 */
public class ObjectDataFactory {
    private static String path = "object/MultObjects.json";
    private static String[] loadDataName = {"err", "right","optionRight","optionErr"};
    private static Map<String, Integer> maxMap = new HashMap<>();
    public static Map<String, ArrayList<MetaData>> arrayData = null;

    static {
        arrayData = load(path);
    }

    @DataProvider(name = "right")
    public Object[] rightData() {
        return arrayData.get("right").toArray(new MetaData[maxMap.get("right")]);
    }

    @DataProvider(name = "err")
    public Object[] errData() {
        return arrayData.get("err").toArray(new MetaData[maxMap.get("err")]);
    }

    @DataProvider(name = "optionRight")
    public Object[] optionRightData(){
        return arrayData.get("optionRight").toArray(new MetaData[maxMap.get("optionRight")]);
    }
    @DataProvider(name = "optionErr")
    public Object[] optionErrData(){
        return arrayData.get("optionErr").toArray(new MetaData[maxMap.get("optionErr")]);
    }
    /**
     * 加载数据
     * @param path 文件路径
     * @return Map 包含正确用例与错误用例
     */
    private static Map<String, ArrayList<MetaData>> load(String path) {
        Map<String, ArrayList<MetaData>> arrayData = new HashMap<>();
        String expectContent = ResourceUtils.loadResourceLine(path);
        JSONObject jsonObject = (JSONObject) JSON.parse(expectContent);
        for (String name : loadDataName) {
            arrayData.put(name, JsonArrayToMetadataList(jsonObject.getJSONArray(name), name));
        }
        return arrayData;
    }

    /**
     * 将JsonArray对象转换为List<Metadata> 并将每种类型的个数进行统计赋值
     *
     * @param jsonArray 需要转换的对象
     * @param type      他是什么类型
     * @return
     */
    private static ArrayList<MetaData> JsonArrayToMetadataList(JSONArray jsonArray, String type) {
        ArrayList<MetaData> list = new ArrayList<>();
        int count = 0;
        for (Object i : jsonArray) {
            JSONObject jsonObject = (JSONObject) i;
            MetaData metaData = new MetaData(jsonObject.get("expect"),
                    jsonObject.get("actual"),
                    jsonObject.get("ret"),
                    getOptionObject((JSONObject) jsonObject.get("option")));
            list.add(metaData);
            ++count;
        }
        SetMax(type, count);
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
            ignoreOrder =  jsonObject.getBoolean("ignoreOrder") == null?false:jsonObject.getBoolean("ignoreOrder");
        }catch (ExceptionInInitializerError e){
            e.printStackTrace();
        }
        jsonComparedOption.setIgnoreOrder(ignoreOrder);
        jsonComparedOption.setMapping((Map) jsonObject.get("mapping"));
        jsonComparedOption.setIgnorePath(JSON.parseArray(jsonObject.getString("ignorePath"), String.class));
        jsonComparedOption.setIgnoreKey(JSON.parseArray(jsonObject.getString("ignoreKey"), String.class));
        return jsonComparedOption;
    }

    /**
     * 用于将得到的每个最大值进行赋值
     *
     * @param type
     * @param count
     */
    private static void SetMax(String type, int count) {
        for (String name : loadDataName) {
            maxMap.put(name, count - 1);
        }
    }
}
