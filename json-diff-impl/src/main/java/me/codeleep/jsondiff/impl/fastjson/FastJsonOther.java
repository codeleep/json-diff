package me.codeleep.jsondiff.impl.fastjson;

import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffOther;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description: 数组
 */
public class FastJsonOther implements JsonDiffOther {

    private final Object object;

    public FastJsonOther(Object object) {
        this.object = object;
    }

    @Override
    public Object getOther() {
        return object;
    }

    @Override
    public boolean isEquals(JsonDiff jsonDiff) {
        // TODO 判断无法识别对象是否一致
        return false;
    }

    @Override
    public Object format() {
        return String.valueOf(object);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
