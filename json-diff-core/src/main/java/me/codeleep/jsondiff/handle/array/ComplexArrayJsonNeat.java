package me.codeleep.jsondiff.handle.array;

import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.utils.JsonCompareUtil;
import me.codeleep.jsondiff.utils.PathUtil;

import java.util.Map;
import java.util.stream.Collectors;

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
        Map<Object, Long> expectMap = expect.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        Map<Object, Long> actualMap = actual.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));





        return null;
    }

    @Override
    public JsonCompareResult keepOrder(JSONArray expect, JSONArray actual) {
        int len = expect.size();
        for (int i = 0; i < len; i++) {
            // 数据都是基础类型
            JsonCompareResult jsonCompareResult = JsonCompareUtil.handlePrimitiveType(expect.get(i), actual.get(i), PathUtil.getIndexPath(this.path, i));
            if (!jsonCompareResult.isMatch()) {
                result.mergeDefects(jsonCompareResult.getDefectsList());
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
