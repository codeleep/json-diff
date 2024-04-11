package me.codeleep.jsondiff.impl.fastjson2;

import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffOther;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description:
 */
public class FastJson2Other implements JsonDiffOther {

    private final Object object;

    public FastJson2Other(Object object) {
        this.object = object;
    }


    @Override
    public Object getOther() {
        return object;
    }

    @Override
    public boolean isEquals(JsonDiff jsonDiff) {
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
