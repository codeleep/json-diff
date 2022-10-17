package me.codeleep.jsondiff.jsonimpl.fastjson;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.model.json.DiffJsonArray;

/**
 * @author: codeleep
 * @createTime: 2022/09/28 22:57
 * @description:
 */
public class FastJsonArray implements DiffJsonArray {

    private JSONArray target;

    @Override
    public int size() {
        return target.size();
    }

    @Override
    public Object[] toArray() {
        return target.toArray();
    }

    @Override
    public DiffJsonArray build(String text) {
        target = JSON.parseArray(text);
        return this;
    }
}
