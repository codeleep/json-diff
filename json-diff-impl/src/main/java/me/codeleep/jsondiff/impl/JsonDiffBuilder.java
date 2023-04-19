package me.codeleep.jsondiff.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.impl.fastjson2.FastJson2Array;
import me.codeleep.jsondiff.impl.fastjson2.FastJson2Object;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:33
 * @description: 对象构造器
 */
public class JsonDiffBuilder {

    public JsonDiffObject buildObject(JSONObject jsonObject) {
        return new FastJson2Object(jsonObject);
    }

    public FastJson2Array buildObject(JSONArray jsonArray) {
        return new FastJson2Array(jsonArray);
    }

    // TODO 更多的实现

}
