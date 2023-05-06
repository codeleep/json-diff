package me.codeleep.jsondiff.impl.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;

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
    public Object get(int index) {
        if (jsonArray == null) {
            return null;
        }
        Object value = jsonArray.get(index);
        if (value instanceof ArrayNode) {
            return new JacksonArray((ArrayNode) value);
        }
        if (value instanceof ObjectNode) {
            return new JacksonObject((ObjectNode) value);
        }
        return value;
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
}
