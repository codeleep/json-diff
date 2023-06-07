package me.codeleep.jsondiff.impl.fastjson2;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description: 数组
 */
public class FastJson2Array implements JsonDiffArray {

    private final JSONArray jsonArray;

    public FastJson2Array(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @Override
    public int size() {
        if (jsonArray == null) {
            return 0;
        }
        return jsonArray.size();
    }

    @Override
    public Object get(int index) {
        if (jsonArray == null) {
            return null;
        }
        Object value = jsonArray.get(index);
        if (value instanceof JSONArray) {
            return new FastJson2Array((JSONArray) value);
        }
        if (value instanceof JSONObject) {
            return new FastJson2Object((JSONObject) value);
        }
        return value;
    }

    @Override
    public void add(Object item) {
        jsonArray.add(item);
    }

    @Override
    public void addAll(Collection<?> c) {
        jsonArray.addAll(c);
    }

    @Override
    public Object format() {
        return JSON.to(ArrayList.class, jsonArray);
    }
}
