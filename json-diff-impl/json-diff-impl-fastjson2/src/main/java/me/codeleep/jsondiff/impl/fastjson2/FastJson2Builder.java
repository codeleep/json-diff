package me.codeleep.jsondiff.impl.fastjson2;

import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonBuilder;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 下午7:10
 * @description:
 */
public class FastJson2Builder implements JsonBuilder {
    @Override
    public JsonDiff builder(String json) {
        Object parse = com.alibaba.fastjson2.JSON.parse(json);
        if (parse instanceof com.alibaba.fastjson2.JSONObject) {
            return new FastJson2Object((com.alibaba.fastjson2.JSONObject)parse);
        }
        if (parse instanceof com.alibaba.fastjson2.JSONArray) {
            return new FastJson2Array((com.alibaba.fastjson2.JSONArray)parse);
        }
        return null;
    }
}
