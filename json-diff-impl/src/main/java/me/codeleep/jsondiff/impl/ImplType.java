package me.codeleep.jsondiff.impl;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 20:42
 * @description: 实现类型
 */
public enum ImplType {

    FASTJSON("fastjson"),
    FASTJSON2("fastjson2"),
    JACKSON("jackson"),
    GSON("gson");

    private String type;

    ImplType(String type) {
        this.type = type;
    }


}
