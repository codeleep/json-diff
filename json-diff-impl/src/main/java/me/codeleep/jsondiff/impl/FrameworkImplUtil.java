package me.codeleep.jsondiff.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.impl.fastjson.FastJsonArray;
import me.codeleep.jsondiff.impl.fastjson.FastJsonObject;
import me.codeleep.jsondiff.impl.fastjson2.FastJson2Array;
import me.codeleep.jsondiff.impl.fastjson2.FastJson2Object;
import me.codeleep.jsondiff.impl.gson.GsonArray;
import me.codeleep.jsondiff.impl.gson.GsonObject;
import me.codeleep.jsondiff.impl.jackson.JacksonArray;
import me.codeleep.jsondiff.impl.jackson.JacksonObject;

/**
 * @author: codeleep
 * @createTime: 2023/05/06 22:19
 * @description: 框架实现工具类
 */
public class FrameworkImplUtil {

    private static final Gson gson = new Gson();


    private static final ObjectMapper mapper = new ObjectMapper();


    public static JsonDiff fastJson(String jsonStr) {
        Object parse = com.alibaba.fastjson.JSON.parse(jsonStr);
        if (parse instanceof com.alibaba.fastjson.JSONObject) {
            return new FastJsonObject((com.alibaba.fastjson.JSONObject)parse);
        }
        if (parse instanceof com.alibaba.fastjson.JSONArray) {
            return new FastJsonArray((com.alibaba.fastjson.JSONArray)parse);
        }
        return null;
    }

    public static JsonDiff fastJson2(String jsonStr) {
        Object parse = com.alibaba.fastjson2.JSON.parse(jsonStr);
        if (parse instanceof com.alibaba.fastjson2.JSONObject) {
            return new FastJson2Object((com.alibaba.fastjson2.JSONObject)parse);
        }
        if (parse instanceof com.alibaba.fastjson2.JSONArray) {
            return new FastJson2Array((com.alibaba.fastjson2.JSONArray)parse);
        }
        return null;
    }

    public static JsonDiff gson(String jsonStr) {
        JsonElement jsonTree = gson.fromJson(jsonStr, JsonElement.class);
        if (jsonTree instanceof com.google.gson.JsonArray) {
            return new GsonArray((com.google.gson.JsonArray)jsonTree);
        }
        if (jsonTree instanceof com.google.gson.JsonObject) {
            return new GsonObject((com.google.gson.JsonObject)jsonTree);
        }
        return null;
    }

    public static JsonDiff jackson(String jsonStr) {
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonStr);
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
