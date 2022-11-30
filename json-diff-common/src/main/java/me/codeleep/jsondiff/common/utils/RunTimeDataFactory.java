package me.codeleep.jsondiff.common.utils;


import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.DiffProcessResultStack;
import me.codeleep.jsondiff.common.model.JsonComparedOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
     * id 序列生成器
     */
    private final static ThreadLocal<Long> sequenceThreadLocal = new ThreadLocal<>();

    /**
     * 运行时差异堆栈
     */
    private final static ThreadLocal<DiffProcessResultStack> stackThreadLocal = new ThreadLocal<>();


    /**
     * 获取对比配置
     * @return 对比配置
     */
    public static JsonComparedOption getOptionInstance() {
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
     * 获取id, 这里似乎不用考虑竞争。直接使用Long即可
     * @return 获取id
     */
    public static long getId() {
        if (sequenceThreadLocal.get() == null) {
            sequenceThreadLocal.set(1L);
        }
        // 依次递增
        long seq = sequenceThreadLocal.get() + 1;
        sequenceThreadLocal.set(seq);
        return seq;
    }


    /**
     * 获取当前的path
     * @param id 当前节点的id
     * @param ex 期望对象 ｜ 真实对象
     * @return 当前节点的路径
     */
    public static String getPath(long id, boolean ex) {
        if (stackThreadLocal.get() == null) {
            throw new JsonDiffException("Found no initialization stackThreadLocal");
        }
        Stack<String> nodeStack = new Stack<>();
        DiffUtils.getPathToTarget(stackThreadLocal.get(), id, nodeStack, ex);
        return DiffUtils.getCurrentPath(nodeStack);
    }


}
