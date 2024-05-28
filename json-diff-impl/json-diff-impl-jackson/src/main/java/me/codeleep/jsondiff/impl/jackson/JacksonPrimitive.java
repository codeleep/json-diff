package me.codeleep.jsondiff.impl.jackson;

import com.fasterxml.jackson.databind.JsonNode;
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
        if (object == null || object instanceof String) {
            return object;
        }
        if (object instanceof JsonNode) {
            return ((JsonNode)this.object).asText();
        }
        return String.valueOf(object);
    }

    @Override
    public boolean isLeaf() {
        return true;
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
    public Object getTarget() {
        return object;
    }
}
