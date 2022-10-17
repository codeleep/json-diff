package me.codeleep.jsondiff.handle;

import me.codeleep.jsondiff.model.CurrentPath;
import me.codeleep.jsondiff.model.JsonCompareResult;
import me.codeleep.jsondiff.model.JsonComparedOption;
import me.codeleep.jsondiff.model.JsonComparedTempData;

import static me.codeleep.jsondiff.handle.JsonDiffConstants.ROOT_PATH;

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
     * 结果
     */
    private static final ThreadLocal<JsonCompareResult> resultThreadLocal = new ThreadLocal<>();

    /**
     * 对比临时辅助数据区。根据currentPath 进行数据隔离
     */
    private static final ThreadLocal<JsonComparedTempData> jsonComparedTempDataThreadLocal = new ThreadLocal<>();

    /**
     * 当前遍历的路径
     */
    private static final ThreadLocal<CurrentPath> currentPathThreadLocal = new ThreadLocal<>();



    public static JsonComparedOption getOptionInstance() {
        if (optionThreadLocal.get() == null) {
            optionThreadLocal.set(new JsonComparedOption());
        }
        // 默认配置
        return optionThreadLocal.get();
    }

    public static void setOptionInstance(JsonComparedOption jsonComparedOption) {
        if (jsonComparedOption == null) {
            return;
        }
        optionThreadLocal.remove();
        optionThreadLocal.set(jsonComparedOption);
    }

    public static JsonCompareResult getResultInstance() {
        if(resultThreadLocal.get() == null) {
            // 默认配置
            JsonCompareResult jsonCompareResult = new JsonCompareResult();
            resultThreadLocal.set(jsonCompareResult);
        }
        return resultThreadLocal.get();
    }

    public static JsonComparedTempData getTempDataInstance() {
        if(jsonComparedTempDataThreadLocal.get() == null) {
            // 默认配置
            JsonComparedTempData jsonComparedTempData = new JsonComparedTempData();
            jsonComparedTempDataThreadLocal.set(jsonComparedTempData);
        }
        return jsonComparedTempDataThreadLocal.get();
    }


    public static CurrentPath getCurrentPathInstance() {
        if(currentPathThreadLocal.get() == null) {
            // 默认配置
            currentPathThreadLocal.set(new CurrentPath());
            currentPathThreadLocal.get().push(ROOT_PATH);
        }
        return currentPathThreadLocal.get();
    }

    public static JsonCompareResult remove() {
        JsonCompareResult jsonCompareResult = resultThreadLocal.get();
        optionThreadLocal.remove();
        resultThreadLocal.remove();
        currentPathThreadLocal.remove();
        jsonComparedTempDataThreadLocal.remove();
        return jsonCompareResult;
    }


}
