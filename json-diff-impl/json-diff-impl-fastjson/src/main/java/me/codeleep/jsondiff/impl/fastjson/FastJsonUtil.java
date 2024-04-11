package me.codeleep.jsondiff.impl.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;

import java.math.BigDecimal;
import java.math.BigInteger;

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
        if (isJavaPrimitive(value)) {
            return new FastJsonPrimitive(value);
        }
        return new FastJsonOther(value);
    }

    public static boolean isJavaPrimitive(Object value) {
        return value instanceof String || value instanceof Integer || value instanceof BigInteger || value instanceof BigDecimal || value instanceof Boolean || value instanceof Double || value instanceof Float || value instanceof Long || value instanceof Short || value instanceof Byte || value instanceof Character;
    }

}
