package me.codeleep.jsondiff.common.model.neat;

import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:14
 * @description:
 */
public interface JsonNeat {
    JsonCompareResult diff(JsonDiffArray expect, JsonDiffArray actual, TravelPath travelPath);
    JsonCompareResult diff(JsonDiffObject expect, JsonDiffObject actual, TravelPath travelPath);
    JsonCompareResult diff(Object expect, Object actual, TravelPath travelPath);

}
