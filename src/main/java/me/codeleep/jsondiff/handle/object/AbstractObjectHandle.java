package me.codeleep.jsondiff.handle.object;

import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.handle.AbstractDiffHandle;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.model.JsonCompareResult;
import me.codeleep.jsondiff.utils.ComparedUtil;

import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 15:25
 * @description: 抽象数组处理器
 */
public abstract class AbstractObjectHandle extends AbstractDiffHandle implements ObjectHandle {


    @Override
    public JsonCompareResult handle(JSONObject expectObject, JSONObject actualObject) {
        // 忽略的path
        if (ComparedUtil.matchIgnoredPath(RunTimeDataFactory.getCurrentPathInstance().getPath(), RunTimeDataFactory.getOptionInstance().getIgnorePath())) {
            return RunTimeDataFactory.getResultInstance();
        }

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
