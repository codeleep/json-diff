package me.codeleep.jsondiff.impl.gson;

import me.codeleep.jsondiff.common.model.neat.JsonDiffPrimitive;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description:
 */
public class GsonPrimitive implements JsonDiffPrimitive {

    private final Object object;

    public GsonPrimitive(Object object) {
        this.object = object;
    }

    @Override
    public Object format() {
        return String.valueOf(object);
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
    @Override
    public boolean isEquals(JsonDiffPrimitive jsonDiffPrimitive) {
        if (jsonDiffPrimitive == null && object == null)  {
            return true;
        }
        if (jsonDiffPrimitive == null) {
            return false;
        }
        Object target = jsonDiffPrimitive.getTarget();
        return (object != null && object.equals(target)) || (target != null && target.equals(object));
    }

    @Override
    public Object getTarget() {
        return object;
    }
}
