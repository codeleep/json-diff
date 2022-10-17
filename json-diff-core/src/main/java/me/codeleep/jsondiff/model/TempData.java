package me.codeleep.jsondiff.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 17:00
 * @description: 对比过程中使用到的临时配置
 */
public class TempData {



    /**
     * 不记录到结果中的临时defects
     */
    private List<Defects> defectsList;

    /**
     *
     * @param ignoreOrder
     * @return
     */
    private int addDiffCount = 0;

    /**
     * 不记录到结果中的临时Path
     */
    private Stack<String> tempPath = new Stack<>();

    public boolean isAddDiff() {
        return addDiffCount == 0;
    }

    public void setAddDiff(boolean addDiff) {
        if (addDiff) {
            this.addDiffCount --;
        }else {
            this.addDiffCount ++;
        }
    }

    /**
     * 清空
     */
    public void clear() {
        if (defectsList != null) {
            defectsList.clear();
        }
    }

    /**
     * 对比信息是否为空
     */
    public boolean isDefectsEmpty() {
        if (defectsList == null) {
            return true;
        }
        return defectsList.size() == 0;
    }


    /**
     * 添加对比信息
     * @param defects
     */
    public void addDefects(Defects defects) {
        if(defectsList == null) {
            defectsList = new ArrayList<>();
        }
        defectsList.add(defects);
    }


    public void push(String key) {
        if (tempPath == null) {
            tempPath = new Stack<>();
        }
        tempPath.push(key);
    }

    public void pop() {
        try {
            tempPath.pop();
        }catch (Exception ignored) {

        }
    }

    public Stack<String> getPath() {
        if (tempPath == null) {
            tempPath = new Stack<>();
        }
        return this.tempPath;
    }


}
