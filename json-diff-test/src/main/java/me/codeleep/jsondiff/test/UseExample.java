package me.codeleep.jsondiff.test;


import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.JsonComparedOption;
import me.codeleep.jsondiff.core.DefaultJsonDifference;

/**
 * @author: codeleep
 * @createTime: 2022/11/22 16:57
 * @description: 使用示例
 */
public class UseExample {
    //
    //public static void main(String[] args) {
    //    String array1 = "[1, 2, 3, 4, 5]";
    //    String array2 = "[1, 3, 9, 4, 5]";
    //
    //    JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true);
    //    JsonCompareResult jsonCompareResult = new DefaultJsonDifference()
    //            .option(jsonComparedOption)
    //            .detectDiff(JSON.parseArray(array1), JSON.parseArray(array2));
    //    System.out.println(JSON.toJSONString(jsonCompareResult));
    //}

    public static void main(String[] args) {
        String object1 = "{\"test\": 23232323232343445676654323}";
        String object2 = "{\"test\": 23232323232343445676654323}";

        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true);
        JsonCompareResult jsonCompareResult = new DefaultJsonDifference()
                .option(jsonComparedOption)
                .detectDiff(JSON.parseObject(object1), JSON.parseObject(object2));
        System.out.println(JSON.toJSONString(jsonCompareResult));
    }
}
