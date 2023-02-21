package me.codeleep.jsondiff.common.model;


import java.util.ArrayList;
import java.util.List;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 19:26
 * @description: 差异结果
 */
public class JsonCompareResult {

    /**
     * 是否符合对比要求
     */
    private Boolean match = true;

    /**
     * 差异列表
     */
    private List<Defects> defectsList;


    public boolean isMatch(){
        if(match == null){
            return  false;
        }
        return match;
    }

    /**
     * 添加对比信息
     * @param defects 不一致的信息
     * @return 返回是否添加成功
     */
    public boolean addDefects(Defects defects) {
        if(defectsList == null) {
            defectsList = new ArrayList<>();
        }
        if (match) {
            match = false;
        }
        defectsList.add(defects);
        return true;
    }

    public void mergeDefects(List<Defects> defectsList) {
        if (defectsList == null || defectsList.size() == 0) {
            return;
        }
        match = false;
        this.defectsList.addAll(defectsList);
    }

    public Boolean getMatch() {
        return match;
    }

    public void setMatch(Boolean match) {
        this.match = match;
    }

    public List<Defects> getDefectsList() {
        return defectsList;
    }

    public void setDefectsList(List<Defects> defectsList) {
        this.defectsList = defectsList;
    }
}
