package me.codeleep.jsondiff.handle.primitive;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.handle.AbstractTypeCheck;
import me.codeleep.jsondiff.neat.JsonNeat;
import me.codeleep.jsondiff.utils.ClassUtil;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:29
 * @description: 抽象比较器
 */
public abstract class AbstractPrimitiveJsonNeat extends AbstractTypeCheck implements JsonNeat {


    @Override
    public JsonCompareResult diff(JSONArray expect, JSONArray actual, String path) {
        throw new JsonDiffException("类型调用错误");
    }

    @Override
    public JsonCompareResult diff(JSONObject expect, JSONObject actual, String path) {
        throw new JsonDiffException("类型调用错误");
    }

    @Override
    public boolean check(Object expect, Object actual) {
        return ClassUtil.isSameClass(expect, actual);
    }

    /**
     * 前置检查
     * @param expect
     * @param actual
     */
    public void preCheck(JSONArray expect, JSONArray actual) {

    }
}
