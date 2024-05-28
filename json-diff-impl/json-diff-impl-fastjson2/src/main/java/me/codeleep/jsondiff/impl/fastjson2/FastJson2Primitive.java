package me.codeleep.jsondiff.impl.fastjson2;

import me.codeleep.jsondiff.common.model.neat.JsonDiffPrimitive;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description:
 */
public class FastJson2Primitive implements JsonDiffPrimitive {

    private final Object object;

    public FastJson2Primitive(Object object) {
        this.object = object;
    }

    @Override
    public boolean isEquals(JsonDiffPrimitive jsonDiffPrimitive) {
        if (jsonDiffPrimitive != null && jsonDiffPrimitive.getTarget() == null && object == null)  {
            return true;
        }
        if (jsonDiffPrimitive == null || jsonDiffPrimitive.getTarget() == null) {
            return false;
        }
        Object target = jsonDiffPrimitive.getTarget();
        return (object != null && object.equals(target)) || (target != null && target.equals(object));
    }

    @Override
    public Object format() {
        if (object == null || object instanceof String) {
            return object;
        }
        return String.valueOf(object);
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Object getTarget() {
        return this.object;
    }
}
