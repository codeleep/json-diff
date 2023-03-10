package me.codeleep.jsondiff.common.model.neat;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:14
 * @description:
 */
public interface JsonNeat {
    JsonCompareResult diff(JSONArray expect, JSONArray actual, TravelPath travelPath);
    JsonCompareResult diff(JSONObject expect, JSONObject actual, TravelPath travelPath);
    JsonCompareResult diff(Object expect, Object actual, TravelPath travelPath);

}
