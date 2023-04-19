package me.codeleep.jsondiff.impl.fastjson2;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.impl.JsonDiffObject;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:20
 * @description: 实现
 */
public class FastJson2Object implements JsonDiffObject {

    private final JSONObject jsonObject;

    public FastJson2Object(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public Object get(String key) {
        if (jsonObject == null) {
            return null;
        }
        Object value = jsonObject.get(key);
        if (value instanceof JSONObject) {
            return new FastJson2Object((JSONObject) value);
        }
        if (value instanceof JSONArray) {
            return new FastJson2Array((JSONArray) value);
        }
        return value;
    }

    @Override
    public Set<String> keySet() {
        if (this.jsonObject == null) {
            return new HashSet<>();
        }
        return this.jsonObject.keySet();
    }

}
