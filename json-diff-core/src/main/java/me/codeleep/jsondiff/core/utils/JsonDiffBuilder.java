package me.codeleep.jsondiff.core.utils;

import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.core.config.JsonDiffOption;
import me.codeleep.jsondiff.common.utils.ImplType;

import static me.codeleep.jsondiff.common.utils.ImplType.*;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:33
 * @description: 对象构造器
 */
public class JsonDiffBuilder {

    public static JsonDiff buildObject(String jsonStr) {
        ImplType defaultJsonFramework = JsonDiffOption.getDefaultJsonFramework();
        switch (defaultJsonFramework) {
            case FASTJSON: return FASTJSON.getBuilder().builder(jsonStr);
            case FASTJSON2: return FASTJSON2.getBuilder().builder(jsonStr);
            case GSON: return GSON.getBuilder().builder(jsonStr);
            case JACKSON: return JACKSON.getBuilder().builder(jsonStr);
        }
        return null;
    }

}
