package me.codeleep.jsondiff.core.handle.object;


import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.core.handle.AbstractTypeCheck;
import me.codeleep.jsondiff.core.neat.ObjectJsonNeat;

import java.util.HashSet;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:29
 * @description: 抽象比较器
 */
public abstract class AbstractObjectJsonNeat extends AbstractTypeCheck implements ObjectJsonNeat {


    @Override
    public JsonCompareResult diff(JSONArray expect, JSONArray actual, TravelPath travelPath) {
        throw new JsonDiffException("类型调用错误");
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
