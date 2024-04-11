package me.codeleep.jsondiff.impl.fastjson2;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 下午1:46
 * @description:
 */
public class FastJson2Util {

    public static JsonDiff formatJsonDiff(Object value) {
        if (value instanceof JSONArray) {
            return new FastJson2Array((JSONArray) value);
        }
        if (value instanceof JSONObject) {
            return new FastJson2Object((JSONObject) value);
        }
        if (isJavaPrimitive(value)) {
            return new FastJson2Primitive(value);
        }
        return new FastJson2Other(value);
    }

    public static boolean isJavaPrimitive(Object value) {
        return value instanceof String || value instanceof Integer || value instanceof BigInteger || value instanceof BigDecimal || value instanceof Boolean || value instanceof Double || value instanceof Float || value instanceof Long || value instanceof Short || value instanceof Byte || value instanceof Character;
    }

}
