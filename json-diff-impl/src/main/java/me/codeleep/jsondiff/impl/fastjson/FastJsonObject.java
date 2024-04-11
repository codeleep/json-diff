package me.codeleep.jsondiff.impl.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:20
 * @description: 实现
 */
public class FastJsonObject implements JsonDiffObject {

    private final JSONObject jsonObject;

    public FastJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public JsonDiff get(String key) {
        if (jsonObject == null) {
            return null;
        }
        Object value = jsonObject.get(key);
        if (value instanceof JSONObject) {
            return new FastJsonObject((JSONObject) value);
        }
        if (value instanceof JSONArray) {
            return new FastJsonArray((JSONArray) value);
        }
        return new FastJsonOther(value);
    }

    @Override
    public Set<String> keySet() {
        if (this.jsonObject == null) {
            return new HashSet<>();
        }
        return this.jsonObject.keySet();
    }

    @Override
    public Object format() {
        return JSON.to(HashMap.class, jsonObject);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

}
