package me.codeleep.jsondiff;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.JsonComparedOption;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.handle.array.ComplexArrayJsonNeat;
import me.codeleep.jsondiff.handle.object.ComplexObjectJsonNeat;

import static me.codeleep.jsondiff.common.model.Constant.PATH_ROOT;

/**
 * @author: codeleep
 * @createTime: 2023/02/25 23:43
 * @description: 面向外部的类
 */
public class DefaultJsonDifference {

    public JsonCompareResult detectDiff(JSONObject expect, JSONObject actual) {
        return  new ComplexObjectJsonNeat().diff(expect, actual , PATH_ROOT);
    }

    public JsonCompareResult detectDiff(JSONArray expect, JSONArray actual) {
        return  new ComplexArrayJsonNeat().diff(expect, actual , PATH_ROOT);
    }

    public DefaultJsonDifference option(JsonComparedOption option) {
        RunTimeDataFactory.setOptionInstance(option);
        return this;
    }

}
