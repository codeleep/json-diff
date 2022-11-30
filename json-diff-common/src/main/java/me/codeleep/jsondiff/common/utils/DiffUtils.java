package me.codeleep.jsondiff.common.utils;

import me.codeleep.jsondiff.common.model.DiffProcessResultStack;

import java.util.List;
import java.util.Stack;

import static me.codeleep.jsondiff.common.model.Constant.SIGN;

/**
 * @author: codeleep
 * @createTime: 2022/11/26 12:54
 * @description: 工具方法
 */
public class DiffUtils {



    /**
     * 深度遍历找到对应id的path
     * @param node 根节点
     * @param id 需要查找的id
     * @param path 结果路径
     * @param ex 选择遍历的对象。期望对象 ｜ 真实对象
     * @return 是否符合查找要求
     */
    public static boolean getPathToTarget(DiffProcessResultStack node, long id, Stack<String> path, boolean ex) {
        if (node == null){
            return false;
        }
        // 遍历哪个节点
        if (ex) {
            path.push(node.getExpectNodeName());
        }else {
            path.push(node.getActualNodeName());
        }
        if (node.getId() == id){
            return true;
        }
        if (node.getStacks() == null) {
            return false;
        }
        for (DiffProcessResultStack s: node.getStacks()) {
            if (getPathToTarget(s, id, path, ex)) {
                return true;
            }
        }
        path.pop();
        return false;
    }


    /**
     * 处理json path
     * @return
     */
    public static String getCurrentPath(Stack<String> strings) {
        String[] paths = strings.toArray(new String[0]);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            stringBuilder.append(paths[i]);
            if (i >= paths.length -1) {
                continue;
            }
            if (!(paths[i + 1].startsWith("[") && paths[i + 1].endsWith("]"))) {
                stringBuilder.append(SIGN);
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 是否符合忽略的path
     * @return
     */
    public static boolean matchIgnoredPath(Stack<String> stack, List<String> ignorePath) {
        if (stack == null || ignorePath == null) {
            return false;
        }
        String currentPath = getCurrentPath(stack);
        for (String path: ignorePath) {
            if (StringUtil.pathPattern(currentPath, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 格式化数组表示
     * @param index
     * @return
     */
    public static String formatArrayIndex(int index) {
        return String.format("[%d]", index);
    }


}
