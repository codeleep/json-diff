package me.codeleep.jsondiff.impl.jackson;

import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffPrimitive;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description:
 */
public class JacksonPrimitive implements JsonDiffPrimitive {

    private final Object object;

    public JacksonPrimitive(Object object) {
        this.object = object;
    }

    @Override
    public Object format() {
        return String.valueOf(object);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
    @Override
    public boolean isEquals(JsonDiff jsonDiff) {
        return false;
    }
}
