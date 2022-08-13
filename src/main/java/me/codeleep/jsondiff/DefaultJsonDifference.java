package me.codeleep.jsondiff;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.handle.HandleExampleFactory;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.handle.array.AbstractArrayHandle;
import me.codeleep.jsondiff.handle.object.AbstractObjectHandle;
import me.codeleep.jsondiff.model.JsonCompareResult;
import me.codeleep.jsondiff.model.JsonComparedOption;
import me.codeleep.jsondiff.utils.JsonDiffUtil;

public class DefaultJsonDifference implements JsonDifference{

    @Override
    public JsonCompareResult detectDiff(JSONObject expect, JSONObject actual) {
        AbstractObjectHandle handle = (AbstractObjectHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getObjectHandleClass(expect, actual));
        handle.handle(expect, actual);
        return RunTimeDataFactory.remove();
    }

    @Override
    public JsonCompareResult detectDiff(JSONArray expect, JSONArray actual) {
        AbstractArrayHandle handle = (AbstractArrayHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getArrayHandleClass(expect, actual));
        handle.handle(expect, actual);
        return RunTimeDataFactory.remove();
    }

    public DefaultJsonDifference option(JsonComparedOption option) {
        RunTimeDataFactory.setOptionInstance(option);
        return this;
    }


}
