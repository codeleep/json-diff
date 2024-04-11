package me.codeleep.jsondiff.impl.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonBuilder;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 下午7:10
 * @description:
 */
public class GsonBuilder implements JsonBuilder {
    private final Gson gson = new Gson();
    @Override
    public JsonDiff builder(String json) {
        JsonElement jsonTree = gson.fromJson(json, JsonElement.class);
        if (jsonTree instanceof com.google.gson.JsonArray) {
            return new GsonArray((com.google.gson.JsonArray)jsonTree);
        }
        if (jsonTree instanceof com.google.gson.JsonObject) {
            return new GsonObject((com.google.gson.JsonObject)jsonTree);
        }
        return null;
    }
}
