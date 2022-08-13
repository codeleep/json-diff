package me.codeleep.jsondiff.model;

import lombok.Data;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;

import java.util.ArrayList;
import java.util.List;

@Data
public class JsonCompareResult {

    private Boolean match = true;

    private List<Defects> defectsList;


    public boolean isMatch(){
        if(match == null){
            return  false;
        }
        return match;
    }

    /**
     * 添加对比信息
     * @param defects
     */
    public void addDefects(Defects defects) {
        if(defectsList == null) {
            defectsList = new ArrayList<>();
        }

        // 是否添加差异
        if (RunTimeDataFactory.getTempDataInstance().isAddDiff()) {
            if (match) {
                match = false;
            }
            defectsList.add(defects);
        }else {
            RunTimeDataFactory.getTempDataInstance().addDefects(defects);
        }
    }

}
