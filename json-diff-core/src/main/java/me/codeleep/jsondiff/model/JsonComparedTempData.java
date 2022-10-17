package me.codeleep.jsondiff.model;

import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.utils.JsonDiffUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 17:00
 * @description: 对比过程中使用到的临时配置
 */
public class JsonComparedTempData {

    /**
     * 根据currentPath隔离数据
     */
    private Map<String, TempData> nodeDataMap = new HashMap<>();


    /**
     * 清空
     */
    public void clear() {
        getTempData().clear();
    }

    /**
     * 对比信息是否为空
     */
    public boolean isDefectsEmpty() {
        return getTempData().isDefectsEmpty();
    }


    /**
     * 添加对比信息
     * @param defects
     */
    public void addDefects(Defects defects) {
        getTempData().addDefects(defects);
    }


    /**
     * 获取当前节点的 TempData
     * @return
     */
    private TempData getTempData() {
        String currentPath = JsonDiffUtil.getCurrentPath(RunTimeDataFactory.getCurrentPathInstance().getPath());
        if (nodeDataMap.get(currentPath) == null) {
            nodeDataMap.put(currentPath, new TempData());
        }
        return nodeDataMap.get(currentPath);
    }

    public boolean isAddDiff() {
        return getTempData().isAddDiff();
    }

    public void setAddDiff(boolean addDiff) {
        getTempData().setAddDiff(addDiff);
    }


    /**
     * 临时path
     * @param key
     */

    public void push(String key) {
        getTempData().push(key);
    }

    public void pop() {
        getTempData().pop();
    }

    public Stack<String> getPath() {
        return getTempData().getPath();
    }

}
