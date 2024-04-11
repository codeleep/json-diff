package me.codeleep.jsondiff.common.utils;

import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.neat.JsonBuilder;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 20:42
 * @description: 实现类型
 */
public enum ImplType {

    FASTJSON("fastjson", "me.codeleep.jsondiff.impl.fastjson.FastJsonBuilder"),
    FASTJSON2("fastjson2", "me.codeleep.jsondiff.impl.fastjson2.FastJson2Builder"),
    JACKSON("jackson", "me.codeleep.jsondiff.impl.gson.GsonBuilder"),
    GSON("gson", "me.codeleep.jsondiff.impl.jackson.JacksonBuilder");

    private String type;

    private String builderClassName;

    ImplType(String type, String builderClassName) {
        this.type = type;
        this.builderClassName = builderClassName;
    }

    public JsonBuilder getBuilder() {
        try {
            return (JsonBuilder) Class.forName(builderClassName).newInstance();
        } catch (Exception e) {
            throw new JsonDiffException("Failed to create JsonBuilder instance for " + type, e);
        }
    }

    /**
     * 检测使用哪个json框架
     * @return
     */
    public static ImplType detectJsonParser() {
        for (ImplType implType : ImplType.values()) {
            try {
                Class.forName(implType.builderClassName);
                return implType;
            } catch (ClassNotFoundException e) {
                // Ignore the exception and try the next class name
            }
        }
        throw new JsonDiffException("No JSON parsing framework found, at least one of which implements. fastjson,fastjson2,jackson,gson");
    }
}
