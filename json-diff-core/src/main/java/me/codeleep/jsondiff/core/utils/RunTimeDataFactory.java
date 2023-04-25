package me.codeleep.jsondiff.core.utils;


import me.codeleep.jsondiff.common.model.JsonComparedOption;
import me.codeleep.jsondiff.core.config.JsonDiffOption;

/**
 * @author: codeleep
 * @createTime: 2022/07/31 00:44
 * @description: 运行时数据工厂
 */
public class RunTimeDataFactory {


    /**
     * 配置
     */
    private final static ThreadLocal<JsonComparedOption> optionThreadLocal = new ThreadLocal<>();

    /**
     * 获取对比配置
     * @return 对比配置
     */
    public static JsonComparedOption getOptionInstance() {
        if (JsonDiffOption.isUniqueOption()) {
            return JsonDiffOption.getGloballyUniqueOption();
        }
        if (optionThreadLocal.get() == null) {
            optionThreadLocal.set(new JsonComparedOption());
        }
        // 默认配置
        return optionThreadLocal.get();
    }

    /**
     * s设置对比配置
     * @param jsonComparedOption 对比配置
     */
    public static void setOptionInstance(JsonComparedOption jsonComparedOption) {
        if (jsonComparedOption == null) {
            return;
        }
        optionThreadLocal.remove();
        optionThreadLocal.set(jsonComparedOption);
    }

    /**
     * 清理ThreadLocal
     */
    public static void clearOptionInstance() {
        optionThreadLocal.remove();
    }

}
