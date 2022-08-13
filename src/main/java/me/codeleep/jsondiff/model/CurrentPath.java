package me.codeleep.jsondiff.model;

import me.codeleep.jsondiff.handle.RunTimeDataFactory;

import java.util.Stack;

/**
 * @author: codeleep
 * @createTime: 2022/08/09 18:42
 * @description: 当前遍历的路径
 */
public class CurrentPath {

    private Stack<String> path;


    public void push(String key) {
        if (path == null) {
            path = new Stack<>();
        }
        if (!RunTimeDataFactory.getTempDataInstance().isAddDiff()) {
            RunTimeDataFactory.getTempDataInstance().push(key);
            return;
        }
        path.push(key);
    }

    public void pop() {
        if (!RunTimeDataFactory.getTempDataInstance().isAddDiff()) {
            RunTimeDataFactory.getTempDataInstance().pop();
            return;
        }
        try {
            path.pop();
        }catch (Exception ignored) {

        }
    }

    public Stack<String> getPath() {
        if (path == null) {
            path = new Stack<>();
        }
        return this.path;
    }
}
