package me.codeleep.jsondiff.handle.array;

import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.neat.JsonNeat;
import me.codeleep.jsondiff.utils.JsonDiffUtil;
import me.codeleep.jsondiff.utils.PathUtil;

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
        preCheck(expect, actual);
        boolean ignoreOrder = RunTimeDataFactory.getOptionInstance().isIgnoreOrder();
        // 测试
        if (ignoreOrder) {
            return ignoreOrder(expect, actual);
        }
        return keepOrder(expect, actual);
    }

    @Override
    public JsonCompareResult ignoreOrder(JSONArray expect, JSONArray actual) {

        return null;
    }

    @Override
    public JsonCompareResult keepOrder(JSONArray expect, JSONArray actual) {
        int len = expect.size();
        for (int i = 0; i < len; i++) {
            // 判断类型, 根据类型去实例化JsonNeat。
            JsonNeat jsonNeat = JsonDiffUtil.getJsonNeat(expect.get(i), actual.get(i));
            // 类型不一致
            if (jsonNeat == null) {
                Defects defects = new Defects()
                        .setActual(actual)
                        .setExpect(expect)
                        .setIndexPath(path)
                        .setIllustrateTemplate(DATA_TYPE_INCONSISTENT, expect.getClass().getName(), actual.getClass().getName());
                result.addDefects(defects);
                continue;
            }
            // 比较非基础类型
            JsonCompareResult diff = jsonNeat.diff(expect, actual, PathUtil.getIndexPath(path, i));
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

}
