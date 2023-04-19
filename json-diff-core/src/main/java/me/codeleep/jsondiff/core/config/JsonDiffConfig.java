package me.codeleep.jsondiff.core.config;

import me.codeleep.jsondiff.core.utils.JsonNeatFactory;
import me.codeleep.jsondiff.impl.ImplType;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 13:33
 * @description: 全局配置
 */
public class JsonDiffConfig {

    /**
     * 默认的比较器工厂
     */
    private final static JsonNeatFactory jsonNeatFactory = new JsonNeatFactory();

    /**
     * 默认的json框架
     */
    private final static ImplType DEFAULT_JSON_FRAMEWORK = ImplType.FASTJSON2;


    public static JsonNeatFactory getJsonNeatFactory() {
        return jsonNeatFactory;
    }

}
