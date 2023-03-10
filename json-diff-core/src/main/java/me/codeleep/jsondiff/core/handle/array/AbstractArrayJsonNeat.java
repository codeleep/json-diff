package me.codeleep.jsondiff.core.handle.array;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.core.handle.AbstractTypeCheck;
import me.codeleep.jsondiff.common.model.neat.ArrayJsonNeat;

import java.util.HashSet;

import static me.codeleep.jsondiff.common.model.Constant.INCONSISTENT_ARRAY_LENGTH;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:29
 * @description: 抽象比较器
 */
public abstract class AbstractArrayJsonNeat extends AbstractTypeCheck implements ArrayJsonNeat {

    /**
     * 路径
     */
    protected TravelPath travelPath;

    @Override
    public JsonCompareResult diff(JSONObject expect, JSONObject actual, TravelPath travelPath) {
        throw new JsonDiffException("类型调用错误");
    }

    @Override
    public JsonCompareResult diff(Object expect, Object actual, TravelPath travelPath) {
        return diff((JSONArray) expect, (JSONArray) actual, travelPath);
    }

    @Override
    public JsonCompareResult diff(JSONArray expect, JSONArray actual,TravelPath travelPath) {
        this.travelPath = travelPath;
        return detectDiff(expect, actual);
    }

    @Override
    public boolean check(Object expect, Object actual, JsonCompareResult result, TravelPath travelPath) {
        HashSet<String> ignorePath = RunTimeDataFactory.getOptionInstance().getIgnorePath();
        if (ignorePath.contains(travelPath.getAbstractTravelPath())) {
            return false;
        }
        if (expect == null && actual == null) {
            return false;
        }

        if (expect == null || actual == null) {
            return false;
        }

        int expectSize = ((JSONArray) expect).size();
        int actualSize = ((JSONArray) actual).size();

        // 长度不一致
        if (expectSize != actualSize) {
            Defects defects = new Defects()
                    .setActual(actualSize)
                    .setExpect(expectSize)
                    .setTravelPath(travelPath)
                    .setIllustrateTemplate(INCONSISTENT_ARRAY_LENGTH, String.valueOf(expectSize), String.valueOf(actualSize));
            result.addDefects(defects);
            return false;
        }

        return true;
    }

}
