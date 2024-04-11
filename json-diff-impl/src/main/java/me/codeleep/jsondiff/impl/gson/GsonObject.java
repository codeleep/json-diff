package me.codeleep.jsondiff.impl.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:20
 * @description: 实现
 */
public class GsonObject implements JsonDiffObject {

    private static final Gson gson = new Gson();

    private final JsonObject jsonObject;

    public GsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public JsonDiff get(String key) {
        if (jsonObject == null) {
            return null;
        }
        Object value = jsonObject.get(key);
        if (value instanceof JsonObject) {
            return new GsonObject((JsonObject) value);
        }
        if (value instanceof JsonArray) {
            return new GsonArray((JsonArray) value);
        }
        if (value instanceof JsonPrimitive) {
            return new GsonPrimitive(value);
        }
        return new GsonOther(value);
    }

    @Override
    public Set<String> keySet() {
        if (this.jsonObject == null) {
            return new HashSet<>();
        }
        return this.jsonObject.keySet();
    }

    @Override
    public Object format() {
        return gson.fromJson(jsonObject, HashMap.class);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

}
