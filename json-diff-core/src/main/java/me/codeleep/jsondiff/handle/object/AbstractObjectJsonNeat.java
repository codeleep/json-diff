package me.codeleep.jsondiff.handle.object;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.handle.AbstractTypeCheck;
import me.codeleep.jsondiff.neat.ObjectJsonNeat;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:29
 * @description: 抽象比较器
 */
public abstract class AbstractObjectJsonNeat extends AbstractTypeCheck implements ObjectJsonNeat {


    @Override
    public JsonCompareResult diff(JSONArray expect, JSONArray actual, String path) {
        throw new JsonDiffException("类型调用错误");
    }

    /**
     * 前置检查
     * @param expect
     * @param actual
     */
    public void preCheck(JSONObject expect, JSONObject actual) {

    }

}
