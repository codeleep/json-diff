package me.codeleep.jsondiff.common.model.neat;

import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:14
 * @description:
 */
public interface JsonNeat<T extends JsonDiff> {

    JsonCompareResult diff();

    int leaf();

    JsonCompareResult getResult();

    TravelPath getTravelPath();

    T getExpectJsonDiff();

    T getActualJsonDiff();

}
