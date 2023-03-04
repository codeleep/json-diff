package me.codeleep.jsondiff.core.handle.array;

import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.core.neat.JsonNeat;
import me.codeleep.jsondiff.core.utils.JsonDiffUtil;
import me.codeleep.jsondiff.core.utils.PathUtil;

import static me.codeleep.jsondiff.common.model.Constant.DATA_TYPE_INCONSISTENT;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:24
 * @description:
 */
public class ComplexArrayJsonNeat extends AbstractArrayJsonNeat {

    /**
     * 路径
     */
    private String path;

    /**
     * 当前节点比较结果
     */
    private final JsonCompareResult result = new JsonCompareResult();

    @Override
    public JsonCompareResult detectDiff(JSONArray expect, JSONArray actual) {
        // 前置校验失败
        if (!check(expect, actual, result, path)) {
            return result;
        }
        boolean ignoreOrder = RunTimeDataFactory.getOptionInstance().isIgnoreOrder();
        // 测试
        if (ignoreOrder) {
            return ignoreOrder(expect, actual);
        }
        return keepOrder(expect, actual);
    }

    @Override
    public JsonCompareResult ignoreOrder(JSONArray expect, JSONArray actual) {
        int expectIndex = 0;
        int actualIndex = 0;
        int len = expect.size();
        boolean[] expectFlag = new boolean[len];
        boolean[] actualFlag = new boolean[len];


        // 判断出所有能匹配的数据
        for (expectIndex = 0; expectIndex < len; expectIndex++) {
            if (expectFlag[expectIndex]) {
                continue;
            }
            for (actualIndex = 0; actualIndex < len; actualIndex++) {
                if (actualFlag[actualIndex]) {
                    continue;
                }
                JsonNeat jsonNeat = JsonDiffUtil.getJsonNeat(expect.get(expectIndex), actual.get(actualIndex));
                if (jsonNeat == null) {
                    continue;
                }
                JsonCompareResult compareResult = jsonNeat.diff(expect.get(expectIndex), actual.get(actualIndex), PathUtil.getIndexPath(this.path, actualIndex));
                if (compareResult != null && compareResult.isMatch()) {
                    expectFlag[expectIndex] = true;
                    actualFlag[actualIndex] = true;
                }
            }
        }

        // 对所有未匹配数据进行报错
        for (expectIndex = 0; expectIndex < len; expectIndex++) {
            if (expectFlag[expectIndex]) {
                continue;
            }
            for (actualIndex = 0; actualIndex < len; actualIndex++) {
                if (actualFlag[actualIndex]) {
                    continue;
                }
                Object expectItem = expect.get(expectIndex);
                Object actualItem = actual.get(actualIndex);
                // 判断类型, 根据类型去实例化JsonNeat。
                JsonNeat jsonNeat = JsonDiffUtil.getJsonNeat(expectItem, actualItem);
                // 类型不一致
                if (jsonNeat != null) {
                    JsonCompareResult diff = jsonNeat.diff(expectItem, actualItem, PathUtil.getIndexPath(path, actualIndex));
                    // 将结果合并
                    if (!diff.isMatch()) {
                        result.mergeDefects(diff.getDefectsList());
                    }
                    continue;
                }
                //
                Defects defects = new Defects()
                        .setActual(actualItem)
                        .setExpect(expectItem)
                        .setIndexPath(path)
                        .setIllustrateTemplate(DATA_TYPE_INCONSISTENT, expectItem.getClass().getName(), actualItem.getClass().getName());
                result.addDefects(defects);

            }
        }
        return result;
    }

    @Override
    public JsonCompareResult keepOrder(JSONArray expect, JSONArray actual) {
        int len = expect.size();
        for (int i = 0; i < len; i++) {
            Object expectItem = expect.get(i);
            Object actualItem = actual.get(i);
            // 判断类型, 根据类型去实例化JsonNeat。
            JsonNeat jsonNeat = JsonDiffUtil.getJsonNeat(expectItem, actualItem);
            // 类型不一致
            if (jsonNeat == null) {
                Defects defects = new Defects()
                        .setActual(actualItem)
                        .setExpect(expectItem)
                        .setIndexPath(path)
                        .setIllustrateTemplate(DATA_TYPE_INCONSISTENT, expectItem.getClass().getName(), actualItem.getClass().getName());
                result.addDefects(defects);
                continue;
            }
            JsonCompareResult diff = jsonNeat.diff(expectItem, actualItem, PathUtil.getIndexPath(path, i));
            // 将结果合并
            if (!diff.isMatch()) {
                result.mergeDefects(diff.getDefectsList());
            }
        }
        return result;
    }


    @Override
    public JsonCompareResult diff(JSONArray expect, JSONArray actual, String path) {
        this.path = path;
        return detectDiff(expect, actual);
    }

    @Override
    public JsonCompareResult diff(Object expect, Object actual, String path) {
        return diff((JSONArray) expect, (JSONArray) actual, path);
    }

}
