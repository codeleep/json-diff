package me.codeleep.jsondiff;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.model.JsonCompareResult;

public interface JsonDifference {


    JsonCompareResult detectDiff(JSONObject expect, JSONObject actual) throws JSONException, IllegalAccessException;

    JsonCompareResult detectDiff(JSONArray expect, JSONArray actual) throws JSONException, IllegalAccessException;






}
