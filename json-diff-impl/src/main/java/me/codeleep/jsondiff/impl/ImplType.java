package me.codeleep.jsondiff.impl;

import me.codeleep.jsondiff.common.exception.JsonDiffException;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 20:42
 * @description: 实现类型
 */
public enum ImplType {

    FASTJSON("fastjson", "com.alibaba.fastjson.JSON"),
    FASTJSON2("fastjson2", "com.alibaba.fastjson2.JSON"),
    JACKSON("jackson", "com.fasterxml.jackson.databind.ObjectMapper"),
    GSON("gson", "com.google.gson.Gson");

    private String type;

    private String className;

    ImplType(String type,String className) {
        this.type = type;
        this.className = className;
    }

    /**
     * 检测使用哪个json框架
     * @return
     */
    public static ImplType detectJsonParser() {
        for (ImplType implType : ImplType.values()) {
            try {
                Class.forName(implType.className);
                return implType;
            } catch (ClassNotFoundException e) {
                // Ignore the exception and try the next class name
            }
        }
        throw new JsonDiffException("No JSON resolution framework was found, at least one of which exists fastjson,fastjson2,jackson,gson ");
    }
}
