package me.codeleep.jsondiff.impl.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description: 数组
 */
public class JacksonArray implements JsonDiffArray {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final ArrayNode jsonArray;

    public JacksonArray(ArrayNode jsonArray) {
        this.jsonArray = jsonArray;
    }

    @Override
    public int size() {
        if (jsonArray == null) {
            return 0;
        }
        return jsonArray.size();
    }

    @Override
    public JsonDiff get(int index) {
        if (jsonArray == null) {
            return null;
        }
        return JacksonUtil.formatJsonDiff(jsonArray.get(index));
    }

    @Override
    public void add(Object item) {
        if (item == null) {
            jsonArray.add(NullNode.getInstance());
        }
        JsonNode jsonNode = mapper.valueToTree(item);
        jsonArray.add(jsonNode);
    }

    @Override
    public void addAll(Collection<?> cs) {
        cs.forEach(this::add);
    }

    @Override
    public Object format() {
        if (jsonArray == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(jsonArray);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
