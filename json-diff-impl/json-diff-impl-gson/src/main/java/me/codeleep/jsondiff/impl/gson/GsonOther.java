package me.codeleep.jsondiff.impl.gson;

import com.google.gson.JsonPrimitive;
import me.codeleep.jsondiff.common.model.neat.JsonDiffOther;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description:
 */
public class GsonOther implements JsonDiffOther {

    private final Object object;

    public GsonOther(Object object) {
        this.object = object;
    }

    @Override
    public Object format() {
        if (object == null || object instanceof String) {
            return object;
        }
        if (object instanceof JsonPrimitive) {
            return ((JsonPrimitive) object).getAsString();
        }
        return String.valueOf(object);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Object getOther() {
        return object;
    }

    @Override
    public boolean isEquals(JsonDiffOther jsonDiffOther) {
        if (jsonDiffOther != null && jsonDiffOther.getOther() == null && object == null)  {
            return true;
        }
        if (jsonDiffOther == null || jsonDiffOther.getOther() == null) {
            return false;
        }
        Object target = jsonDiffOther.getOther();
        return (object != null && object.equals(target)) || (target != null && target.equals(object));
    }
}
