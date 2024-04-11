package me.codeleep.jsondiff.impl.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:20
 * @description: 实现
 */
public class JacksonObject implements JsonDiffObject {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final ObjectNode jsonObject;

    public JacksonObject(ObjectNode jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public JsonDiff get(String key) {
        if (jsonObject == null) {
            return null;
        }
        return JacksonUtil.formatJsonDiff(jsonObject.get(key));
    }

    @Override
    public Set<String> keySet() {
        if (this.jsonObject == null) {
            return new HashSet<>();
        }
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonObject.fieldNames(), Spliterator.ORDERED), false).collect(Collectors.toSet());
    }

    @Override
    public Object format() {
        return mapper.convertValue(jsonObject, HashMap.class);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

}
