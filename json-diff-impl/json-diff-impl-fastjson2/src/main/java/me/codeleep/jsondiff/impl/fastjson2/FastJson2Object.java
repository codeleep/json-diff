package me.codeleep.jsondiff.impl.fastjson2;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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
public class FastJson2Object implements JsonDiffObject {

    private final JSONObject jsonObject;

    public FastJson2Object(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public JsonDiff get(String key) {
        if (jsonObject == null) {
            return null;
        }
        return FastJson2Util.formatJsonDiff(jsonObject.get(key));
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
        if (this.jsonObject == null) {
            return null;
        }
        return jsonObject.toJSONString();
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

}
