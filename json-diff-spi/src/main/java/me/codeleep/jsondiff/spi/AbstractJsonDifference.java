package me.codeleep.jsondiff.spi;

import me.codeleep.jsondiff.common.model.Constant;
import me.codeleep.jsondiff.common.model.DiffProcessResultStack;
import me.codeleep.jsondiff.common.utils.DiffUtils;

import java.util.Stack;

/**
 * @author: codeleep
 * @createTime: 2022/11/26 12:21
 * @description: 对比抽象类
 */
public abstract class AbstractJsonDifference implements JsonDifference{

    /**
     * 对比结果
     */
    private final DiffProcessResultStack stack = new DiffProcessResultStack();


    public AbstractJsonDifference() {
        stack.setNodeName(Constant.PATH_ROOT);
    }

    /**
     * 获取指定节点的路径
     * @param id 指定节点id
     * @return 返回路径
     */
    public String getPath(long id) {
        Stack<String> path = new Stack<>();
        DiffUtils.getPathToTarget(stack, id, path);
        return DiffUtils.getCurrentPath(path);
    }



}
