package me.codeleep.jsondiff.impl.jackson;

import com.fasterxml.jackson.databind.node.*;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 下午6:58
 * @description:
 */
public class JacksonUtil {

    public static JsonDiff formatJsonDiff(Object value) {
        if (value instanceof ObjectNode) {
            return new JacksonObject((ObjectNode) value);
        }
        if (value instanceof ArrayNode) {
            return new JacksonArray((ArrayNode) value);
        }
        if (isJackPrimitive(value)) {
            return new JacksonOther(value);
        }
        return new JacksonOther(value);
    }

    public static boolean isJackPrimitive(Object value) {
        return value instanceof TextNode || value instanceof ShortNode || value instanceof NullNode || value instanceof BooleanNode || value instanceof BigIntegerNode || value instanceof DoubleNode || value instanceof FloatNode || value instanceof IntNode || value instanceof LongNode;
    }


}
