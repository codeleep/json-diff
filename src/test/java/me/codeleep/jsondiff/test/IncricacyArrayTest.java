package me.codeleep.jsondiff.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.DefaultJsonDifference;
import me.codeleep.jsondiff.LoadJson;
import me.codeleep.jsondiff.model.JsonCompareResult;
import me.codeleep.jsondiff.model.JsonComparedOption;
import me.codeleep.jsondiff.model.MetaData;
import org.junit.jupiter.api.Test;

/**
 * @author: codeleep
 * @createTime: 2022/08/08 23:09
 * @description: 测试数组元素是对象的情况
 */
public class IncricacyArrayTest extends LoadJson {

    private final static String expectPath = "array/IntricacyArraysActual.json";

    private final static String actualPath = "array/IncricacyArraysActual.json";

    @Test
    public void diffKeepOrder() {
        MetaData metaData = load(expectPath, actualPath);
        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(false);
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(jsonComparedOption)
                .detectDiff((JSONArray) metaData.getExpect(), (JSONArray) metaData.getActual());
        System.out.println(JSON.toJSONString(jsonCompareResult));
    }

    @Test
    public void diffIgnoreOrder() {
        MetaData metaData = load(expectPath, actualPath);
        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true);
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(jsonComparedOption)
                .detectDiff((JSONArray) metaData.getExpect(), (JSONArray) metaData.getActual());
        System.out.println(JSON.toJSONString(jsonCompareResult));
    }



}
