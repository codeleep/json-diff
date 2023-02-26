package me.codeleep.jsondiff.handle.array;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.handle.AbstractTypeCheck;
import me.codeleep.jsondiff.neat.ArrayJsonNeat;

import static me.codeleep.jsondiff.common.model.Constant.INCONSISTENT_ARRAY_LENGTH;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:29
 * @description: 抽象比较器
 */
public abstract class AbstractArrayJsonNeat extends AbstractTypeCheck implements ArrayJsonNeat {


    @Override
    public JsonCompareResult diff(JSONObject expect, JSONObject actual, String path) {
        throw new JsonDiffException("类型调用错误");
    }

    @Override
    public boolean check(Object expect, Object actual, JsonCompareResult result, String path) {
        if (expect == null && actual == null) {
            return false;
        }

        if (expect == null || actual == null) {
            return false;
        }

        int expectSize = ((JSONArray) expect).size();
        int actualSize = ((JSONArray) actual).size();

        // 长度不一致
        if (expectSize != actualSize) {
            Defects defects = new Defects()
                    .setActual(actualSize)
                    .setExpect(expectSize)
                    .setIndexPath(path)
                    .setIllustrateTemplate(INCONSISTENT_ARRAY_LENGTH, String.valueOf(expectSize), String.valueOf(actualSize));
            result.addDefects(defects);
            return false;
        }

        return true;
    }

}
