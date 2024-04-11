package me.codeleep.jsondiff.impl;

import com.fasterxml.jackson.databind.node.*;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 下午2:44
 * @description:
 */
public class TypeCheck {
    public static boolean isJavaPrimitive(Object value) {
        return value instanceof String || value instanceof Integer || value instanceof BigInteger || value instanceof BigDecimal || value instanceof Boolean || value instanceof Double || value instanceof Float || value instanceof Long || value instanceof Short || value instanceof Byte || value instanceof Character;
    }

    public static boolean isJacksonPrimitive(Object value) {
        return value instanceof TextNode || value instanceof ShortNode || value instanceof NullNode || value instanceof BooleanNode || value instanceof BigIntegerNode || value instanceof DoubleNode || value instanceof FloatNode || value instanceof IntNode || value instanceof LongNode;
    }
}
