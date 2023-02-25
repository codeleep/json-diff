package me.codeleep.jsondiff.neat;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.model.JsonCompareResult;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:14
 * @description:
 */
public interface JsonNeat {
    JsonCompareResult diff(JSONArray expect, JSONArray actual, String path);
    JsonCompareResult diff(JSONObject expect, JSONObject actual, String path);
    JsonCompareResult diff(Object expect, Object actual, String path);

}
