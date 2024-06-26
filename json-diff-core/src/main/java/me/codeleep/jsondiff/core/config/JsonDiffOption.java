package me.codeleep.jsondiff.core.config;

import me.codeleep.jsondiff.common.utils.ImplType;

/**
 * @author: codeleep
 * @createTime: 2023/03/10 16:26
 * @description: json diff全局配置选项
 */
public class JsonDiffOption {

    /**
     * 全局配置
     */
    private static JsonComparedOption globallyUniqueOption = new JsonComparedOption();

    /**
     * 是否使用全局配置
     */
    private static boolean uniqueOption = false;

    /**
     * 默认的json框架
     */
    private static ImplType defaultJsonFramework = ImplType.detectJsonParser();

    public static JsonComparedOption getGloballyUniqueOption() {
        if (globallyUniqueOption == null) {
            return new JsonComparedOption();
        }
        return globallyUniqueOption;
    }

    public static void setGloballyUniqueOption(JsonComparedOption globallyUniqueOption) {
        JsonDiffOption.globallyUniqueOption = globallyUniqueOption;
    }

    public static boolean isUniqueOption() {
        return uniqueOption;
    }

    public static void openUniqueOption() {
        JsonDiffOption.uniqueOption = true;
    }

    public static void closeUniqueOption() {
        JsonDiffOption.uniqueOption = false;
    }

    public static void setDefaultJsonFramework(ImplType implType) {
        if (implType == null) {
            return;
        }
        defaultJsonFramework = implType;
    }

    public static ImplType getDefaultJsonFramework() {
        return defaultJsonFramework;
    }
}
