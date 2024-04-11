package me.codeleep.jsondiff.impl.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.impl.TypeCheck;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 下午1:46
 * @description:
 */
public class FastJsonUtil {

    public static JsonDiff formatJsonDiff(Object value) {
        if (value instanceof JSONArray) {
            return new FastJsonArray((JSONArray) value);
        }
        if (value instanceof JSONObject) {
            return new FastJsonObject((JSONObject) value);
        }
        if (TypeCheck.isJavaPrimitive(value)) {
            return new FastJsonPrimitive(value);
        }
        return new FastJsonOther(value);
    }



}
