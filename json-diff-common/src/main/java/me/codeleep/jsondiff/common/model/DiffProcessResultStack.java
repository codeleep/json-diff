package me.codeleep.jsondiff.common.model;

import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: codeleep
 * @createTime: 2022/11/26 11:16
 * @description: 过程结果堆栈
 */
public class DiffProcessResultStack {

    /**
     * 当前的对比路径
     */
    private String path;

    /**
     * 当前真实的的节点名称
     */
    private String actualNodeName;

    /**
     * 当前期望的的节点名称
     */
    private String expectNodeName;

    /**
     * 当前节点的ID
     */
    private long id;

    /**
     * 当前路径差异列表。不包含子节点
     */
    private List<Defects> defectsList;

    /**
     * 子节点堆栈
     */
    private List<DiffProcessResultStack> stacks;

    /**
     * 获取对比堆栈信息存储对象
     * @param expectNodeName 当前节点名称
     * @param actualNodeName 当前节点名称
     * @return 对比堆栈信息存储对象
     */
    public static DiffProcessResultStack build(String expectNodeName, String actualNodeName) {
        DiffProcessResultStack stack = new DiffProcessResultStack();
        stack.setExpectNodeName(expectNodeName);
        stack.setActualNodeName(actualNodeName);
        stack.setId(RunTimeDataFactory.getId());
        return stack;
    }

    public static DiffProcessResultStack build() {
        DiffProcessResultStack stack = new DiffProcessResultStack();
        stack.setId(RunTimeDataFactory.getId());
        return stack;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getActualNodeName() {
        return actualNodeName;
    }

    public void setActualNodeName(String actualNodeName) {
        this.actualNodeName = actualNodeName;
    }

    public String getExpectNodeName() {
        return expectNodeName;
    }

    public void setExpectNodeName(String expectNodeName) {
        this.expectNodeName = expectNodeName;
    }

    public List<Defects> getDefectsList() {
        return defectsList;
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public DiffProcessResultStack addDefects(Defects defects) {
        if (this.defectsList == null) {
            this.defectsList = new ArrayList<>();
        }
        this.defectsList.add(defects);
        return this;
    }

    public List<DiffProcessResultStack> getStacks() {
        return stacks;
    }

    public DiffProcessResultStack addStack(DiffProcessResultStack stack) {
        if (this.stacks == null) {
            this.stacks = new ArrayList<>();
        }
        this.stacks.add(stack);
        return this;
    }
}
