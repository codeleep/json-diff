package me.codeleep.jsondiff.impl.gson;

import com.google.gson.*;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;
import me.codeleep.jsondiff.impl.fastjson.FastJsonOther;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:28
 * @description: 数组
 */
public class GsonArray implements JsonDiffArray {

    private static final Gson gson = new Gson();

    private final JsonArray jsonArray;

    public GsonArray(JsonArray jsonArray) {
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
        Object value = jsonArray.get(index);
        if (value instanceof JsonArray) {
            return new GsonArray((JsonArray) value);
        }
        if (value instanceof JsonObject) {
            return new GsonObject((JsonObject) value);
        }
        return new GsonOther(value);
    }

    @Override
    public void add(Object item) {
        if (item == null) {
            jsonArray.add(JsonNull.INSTANCE);
            return;
        }
        JsonElement jsonTree = gson.toJsonTree(item);
        jsonArray.add(jsonTree);
    }

    @Override
    public void addAll(Collection<?> cs) {
        cs.forEach(this::add);
    }

    @Override
    public Object format() {
        return gson.fromJson(jsonArray, ArrayList.class);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
