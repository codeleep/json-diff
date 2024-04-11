package me.codeleep.jsondiff.impl.fastjson;

import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonBuilder;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 下午7:10
 * @description:
 */
public class FastJsonBuilder implements JsonBuilder {
    @Override
    public JsonDiff builder(String json) {
        Object parse = com.alibaba.fastjson.JSON.parse(json);
        if (parse instanceof com.alibaba.fastjson.JSONObject) {
            return new FastJsonObject((com.alibaba.fastjson.JSONObject)parse);
        }
        if (parse instanceof com.alibaba.fastjson.JSONArray) {
            return new FastJsonArray((com.alibaba.fastjson.JSONArray)parse);
        }
        return null;
    }
}
