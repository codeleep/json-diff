package me.codeleep.jsondiff.jsonimpl.fastjson;

import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.spi.array.DiffJsonArray;

/**
 * @author: codeleep
 * @createTime: 2022/09/28 22:57
 * @description:
 */
public class FastJsonArray implements DiffJsonArray {

    private JSONArray target;

    public FastJsonArray() {
        target = new JSONArray();
    }

    @Override
    public DiffJsonArray getDiffJsonArray(int index) {
        return (DiffJsonArray)super.getJSONArray(index);
    }
}
