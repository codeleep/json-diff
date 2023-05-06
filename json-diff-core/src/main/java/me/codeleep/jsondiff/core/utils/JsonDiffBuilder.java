package me.codeleep.jsondiff.core.utils;

import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.core.config.JsonDiffOption;
import me.codeleep.jsondiff.impl.FrameworkImplUtil;
import me.codeleep.jsondiff.impl.ImplType;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:33
 * @description: 对象构造器
 */
public class JsonDiffBuilder {

    public static JsonDiff buildObject(String jsonStr) {
        ImplType defaultJsonFramework = JsonDiffOption.getDefaultJsonFramework();
        switch (defaultJsonFramework) {
            case FASTJSON: return FrameworkImplUtil.fastJson(jsonStr);
            case FASTJSON2: return FrameworkImplUtil.fastJson2(jsonStr);
            case GSON: return FrameworkImplUtil.gson(jsonStr);
            case JACKSON: return FrameworkImplUtil.jackson(jsonStr);
        }
        return null;
    }

}
