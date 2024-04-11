package me.codeleep.jsondiff.impl.fastjson2;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.impl.TypeCheck;

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
        if (TypeCheck.isJavaPrimitive(value)) {
            return new FastJson2Primitive(value);
        }
        return new FastJson2Other(value);
    }


}
