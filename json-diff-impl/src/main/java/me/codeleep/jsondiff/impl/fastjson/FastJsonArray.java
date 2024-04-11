package me.codeleep.jsondiff.impl.fastjson;

import com.alibaba.fastjson.JSONArray;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;

import java.util.Collection;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description: 数组
 */
public class FastJsonArray implements JsonDiffArray {

    private final JSONArray jsonArray;

    public FastJsonArray(JSONArray jsonArray) {
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
    public JsonDiff get(int index) {
        if (jsonArray == null) {
            return null;
        }
        return FastJsonUtil.formatJsonDiff(jsonArray.get(index));
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
        return String.valueOf(jsonArray);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
