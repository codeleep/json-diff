package me.codeleep.jsondiff.impl.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonBuilder;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 下午7:10
 * @description:
 */
public class JacksonBuilder implements JsonBuilder {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonDiff builder(String json) {
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(json);
        }catch (Exception e) {
            throw new JsonDiffException("jackson 解析json失败");
        }

        if (jsonNode instanceof com.fasterxml.jackson.databind.node.ObjectNode) {
            return new JacksonObject((com.fasterxml.jackson.databind.node.ObjectNode)jsonNode);
        }
        if (jsonNode instanceof com.fasterxml.jackson.databind.node.ArrayNode) {
            return new JacksonArray((com.fasterxml.jackson.databind.node.ArrayNode)jsonNode);
        }
        return null;
    }
}
