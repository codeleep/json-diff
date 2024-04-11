package me.codeleep.jsondiff.impl.fastjson;

import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffOther;
import me.codeleep.jsondiff.common.model.neat.JsonDiffPrimitive;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description: 数组
 */
public class FastJsonPrimitive implements JsonDiffPrimitive {

    private final Object object;

    public FastJsonPrimitive(Object object) {
        this.object = object;
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
        return true;
    }
}
