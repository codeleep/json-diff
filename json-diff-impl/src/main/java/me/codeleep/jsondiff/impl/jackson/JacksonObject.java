package me.codeleep.jsondiff.impl.jackson;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;

import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:20
 * @description: 实现
 */
public class JacksonObject implements JsonDiffObject {

    private final ObjectNode jsonObject;

    public JacksonObject(ObjectNode jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public Object get(String key) {
        if (jsonObject == null) {
            return null;
        }
        Object value = jsonObject.get(key);
        if (value instanceof ObjectNode) {
            return new JacksonObject((ObjectNode) value);
        }
        if (value instanceof ArrayNode) {
            return new JacksonArray((ArrayNode) value);
        }
        return value;
    }

    @Override
    public Set<String> keySet() {
        if (this.jsonObject == null) {
            return new HashSet<>();
        }
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonObject.fieldNames(), Spliterator.ORDERED), false).collect(Collectors.toSet());
    }

}
