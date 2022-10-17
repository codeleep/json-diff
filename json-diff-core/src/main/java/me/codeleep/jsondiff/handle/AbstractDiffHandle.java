package me.codeleep.jsondiff.handle;

import me.codeleep.jsondiff.model.JsonCompareResult;
import me.codeleep.jsondiff.utils.JsonDiffUtil;

import static me.codeleep.jsondiff.handle.JsonDiffConstants.SIGN;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 17:45
 * @description: 顶层抽象
 */
public abstract class AbstractDiffHandle implements Handle{

    protected String getCurrentPath() {
        return JsonDiffUtil.getCurrentPath(RunTimeDataFactory.getCurrentPathInstance().getPath());
    }

    @Override
    public JsonCompareResult getResult() {
        return RunTimeDataFactory.getResultInstance();
    }


    public JsonCompareResult handle() {
        return RunTimeDataFactory.getResultInstance();
    }

}
