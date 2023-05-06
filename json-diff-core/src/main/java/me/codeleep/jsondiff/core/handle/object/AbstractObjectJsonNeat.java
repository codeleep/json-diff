package me.codeleep.jsondiff.core.handle.object;


import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;
import me.codeleep.jsondiff.common.model.neat.ObjectJsonNeat;
import me.codeleep.jsondiff.core.handle.AbstractTypeCheck;
import me.codeleep.jsondiff.core.utils.RunTimeDataFactory;

import java.util.HashSet;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:29
 * @description: 抽象比较器
 */
public abstract class AbstractObjectJsonNeat extends AbstractTypeCheck implements ObjectJsonNeat {

    /**
     * 路径
     */
    protected TravelPath travelPath;


    @Override
    public JsonCompareResult diff(JsonDiffArray expect, JsonDiffArray actual, TravelPath travelPath) {
        throw new JsonDiffException("类型调用错误");
    }

    @Override
    public JsonCompareResult diff(JsonDiffObject expect, JsonDiffObject actual, TravelPath travelPath) {
        this.travelPath = travelPath;
        return detectDiff(expect, actual);
    }

    @Override
    public JsonCompareResult diff(Object expect, Object actual, TravelPath travelPath) {
        return diff((JsonDiffObject) expect, (JsonDiffObject) actual, travelPath);
    }


    @Override
    public boolean check(Object expect, Object actual, JsonCompareResult result, TravelPath travelPath) {
        // 判断该Path有没有被忽略
        HashSet<String> ignorePath = RunTimeDataFactory.getOptionInstance().getIgnorePath();
        if (ignorePath.contains(travelPath.getAbstractTravelPath())) {
            return false;
        }
        if (expect == null && actual == null) {
            return false;
        }
        return true;
    }

}
