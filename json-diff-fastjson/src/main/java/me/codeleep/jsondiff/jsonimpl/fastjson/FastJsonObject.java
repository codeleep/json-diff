package me.codeleep.jsondiff.jsonimpl.fastjson;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.spi.array.DiffJsonArray;
import me.codeleep.jsondiff.spi.object.DiffJsonObject;

import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2022/09/28 22:57
 * @description:
 */
public class FastJsonObject implements DiffJsonObject {

    private JSONObject target;


    @Override
    public Object get(String key) {
        return target.get(key);
    }

    @Override
    public Set<String> keySet() {
        return target.keySet();
    }

    @Override
    public String getString(String key) {
        return target.getString(key);
    }

    @Override
    public Long getLong(String key) {
        return target.getLong(key);
    }

    @Override
    public Boolean getBoolean(String key) {
        return target.getBoolean(key);
    }

    @Override
    public DiffJsonArray getDiffJsonArray(String key) {
        DiffJsonArray diffJsonArray = new FastJsonArray();
        diffJsonArray.build(JSON.toJSONString(target.getJSONArray(key)));
        return diffJsonArray;
    }

    @Override
    public int size() {
        return target.size();
    }

    @Override
    public DiffJsonObject build(String text) {
        target = JSON.parseObject(text);
        return this;
    }
}
