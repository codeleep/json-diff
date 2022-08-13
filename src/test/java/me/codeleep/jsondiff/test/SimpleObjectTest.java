package me.codeleep.jsondiff.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.DefaultJsonDifference;
import me.codeleep.jsondiff.LoadJson;
import me.codeleep.jsondiff.model.JsonCompareResult;
import me.codeleep.jsondiff.model.JsonComparedOption;
import me.codeleep.jsondiff.model.MetaData;
import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * @author: codeleep
 * @createTime: 2022/08/08 23:09
 * @description: 对象
 */
public class SimpleObjectTest extends LoadJson {

    private final static String expectPath = "object/SimpleObjectExpect.json";

    private final static String actualPath = "object/SimpleObjectActual.json";

    @Test
    public void diffKeepOrder() {
        MetaData metaData = load(expectPath, actualPath);
        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(false);
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(jsonComparedOption)
                .detectDiff((JSONObject) metaData.getExpect(), (JSONObject) metaData.getActual());
        System.out.println(JSON.toJSONString(jsonCompareResult));
    }

    @Test
    public void diffIgnoreOrder() {
        MetaData metaData = load(expectPath, actualPath);
        JsonComparedOption jsonComparedOption = new JsonComparedOption()
                .setIgnoreOrder(true)
                .setKeyFunction(path -> {
                    System.out.println(path);
                    return null;
                });
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(jsonComparedOption)
                .detectDiff((JSONObject) metaData.getExpect(), (JSONObject) metaData.getActual());
        System.out.println(JSON.toJSONString(jsonCompareResult));
    }

}
