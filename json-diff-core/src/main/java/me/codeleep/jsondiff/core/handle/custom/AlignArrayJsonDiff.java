package me.codeleep.jsondiff.core.handle.custom;

import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.core.handle.array.ComplexArrayJsonNeat;

import java.util.Collections;

/**
 * @author: codeleep
 * @createTime: 2023/04/25 22:22
 * @description: 将补齐不整齐的数组
 */
public class AlignArrayJsonDiff extends ComplexArrayJsonNeat {

    /**
     * 比较数组.调用入口。需要自己去分别调用 ignoreOrder 和  keepOrder。
     * @param expect 期望的json对象
     * @param actual 实际的json对象
     * @return 返回比较结果
     */
    @Override
    public JsonCompareResult detectDiff(JSONArray expect, JSONArray actual) {
        JsonCompareResult result = new JsonCompareResult();
        // 前置校验失败
        if (!check(expect, actual, result, travelPath)) {
            return result;
        }
        // 长度不一致
        int expectSize = ((JSONArray) expect).size();
        int actualSize = ((JSONArray) actual).size();
        if (expectSize == actualSize) {
            return super.detectDiff(expect, actual);
        }
        JSONArray expectIts = new JSONArray(expect);
        JSONArray actualIts = new JSONArray(actual);
        // 让期望的数组长度和实际的数组长度一致。谁短补齐谁
        if (expectSize > actualSize) {
            actualIts.addAll(Collections.nCopies(expectSize - actualSize, null));
        } else {
            expectIts.addAll(Collections.nCopies(actualSize - expectSize, null));
        }
        return super.detectDiff(expectIts, actualIts);
    }


}
