package me.codeleep.jsondiff.handle.object;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.handle.AbstractDiffHandle;
import me.codeleep.jsondiff.handle.Handle;
import me.codeleep.jsondiff.handle.HandleExampleFactory;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.handle.array.AbstractArrayHandle;
import me.codeleep.jsondiff.handle.array.SimpleArrayHandle;
import me.codeleep.jsondiff.handle.object.ObjectHandle;
import me.codeleep.jsondiff.model.Defects;
import me.codeleep.jsondiff.model.JsonCompareResult;
import me.codeleep.jsondiff.utils.JsonDiffUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 15:25
 * @description: 抽象数组处理器
 */
public abstract class AbstractObjectHandle extends AbstractDiffHandle implements ObjectHandle {


    @Override
    public JsonCompareResult handle(JSONObject expectObject, JSONObject actualObject) {
        // 两个都为null
        if (expectObject == null && actualObject == null) {
            return RunTimeDataFactory.getResultInstance();
        }
        Set<String> expectKeys = expectObject.keySet();
        Set<String> actualKeys = actualObject.keySet();
        // 空对象
        if (expectKeys.size() == 0 && actualKeys.size() == 0) {
            return RunTimeDataFactory.getResultInstance();
        }
        doHandle(expectObject, actualObject);
        return RunTimeDataFactory.getResultInstance();
    }

    protected abstract void doHandle(JSONObject expectObject, JSONObject actualObject);
}
