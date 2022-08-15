package me.codeleep.jsondiff.handle.object;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.handle.HandleExampleFactory;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.handle.array.AbstractArrayHandle;
import me.codeleep.jsondiff.model.Defects;
import me.codeleep.jsondiff.utils.ComparedUtil;
import me.codeleep.jsondiff.utils.JsonDiffUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 19:19
 * @description: 简单json对象对比
 */
public class SimpleObjectHandle extends AbstractObjectHandle {
    @Override
    protected void doHandle(JSONObject expectObject, JSONObject actualObject) {
        Set<String> expectKeys = expectObject.keySet();
        Set<String> actualKeys = actualObject.keySet();

        // 求两种集合的差集
        Set<String> conditionsActual = conditionsActual(expectKeys, actualKeys);
        Set<String> conditionsExpect = conditionsExpect(expectKeys, actualKeys);

        // 对比交集
        Set<String> intersect = intersect(expectKeys, actualKeys);

        // 遍历交集
        for (String key: intersect) {
            RunTimeDataFactory.getCurrentPathInstance().push(key);
            try {
                compared(expectObject.get(key), actualObject.get(key));
            } catch (Exception e) {
                Defects defects = new Defects()
                        .setActual(key)
                        .setExpect(key)
                        .setIndexPath(getCurrentPath())
                        .setIllustrate("field parsing error");
                RunTimeDataFactory.getResultInstance().addDefects(defects);
            }
            RunTimeDataFactory.getCurrentPathInstance().pop();
        }

        // 遍历被映射的集合
        Map<String, String> mapping = RunTimeDataFactory.getOptionInstance().getMapping();
        for (Map.Entry<String, String> entry: mapping.entrySet()) {
            RunTimeDataFactory.getCurrentPathInstance().push(entry.getKey());
            try {
                compared(expectObject.get(entry.getValue()), actualObject.get(entry.getKey()));
            } catch (Exception e) {
                Defects defects = new Defects()
                        .setActual(entry.getKey())
                        .setExpect(entry.getValue())
                        .setIndexPath(getCurrentPath())
                        .setIllustrate("field parsing error");
                RunTimeDataFactory.getResultInstance().addDefects(defects);
            }
            RunTimeDataFactory.getCurrentPathInstance().pop();
        }
    }

    /**
     * actualKeys 有 但是expectKeys 没有的
     * 先匹配出真实存在，但是期望不存在的字段信息。并且这些字段没有被映射, 且没有被忽略
     * @param expectKeys
     * @param actualKeys
     */
    private Set<String> conditionsActual(Set<String> expectKeys, Set<String> actualKeys) {
        Set<String> conditions = new HashSet<>(actualKeys);
        conditions.removeAll(expectKeys);
        Map<String, String> mapping = RunTimeDataFactory.getOptionInstance().getMapping();
        List<String> ignoreKey = RunTimeDataFactory.getOptionInstance().getIgnoreKey();
        for (String key: conditions) {
            if (mapping.get(key) == null && !ignoreKey.contains(key)) {
                Defects defects = new Defects()
                        .setActual(key)
                        .setIndexPath(getCurrentPath())
                        .setIllustrate(String.format("extra field '%s'", key));
                RunTimeDataFactory.getResultInstance().addDefects(defects);
            }
        }
        return conditions;
    }

    /**
     * expectKeys 有 但是actualKeys 没有的
     * 期望字段有，但是真实信息没有的字段信息。并且这些字段信息没有被忽略
     * @param expectKeys
     * @param actualKeys
     */
    private Set<String> conditionsExpect(Set<String> expectKeys, Set<String> actualKeys) {
        Set<String> conditions = new HashSet<>(expectKeys);
        conditions.removeAll(actualKeys);
        List<String> ignoreKey = RunTimeDataFactory.getOptionInstance().getIgnoreKey();
        for (String key: conditions) {
            if (!ignoreKey.contains(key)) {
                Defects defects = new Defects()
                        .setActual(key)
                        .setIndexPath(getCurrentPath())
                        .setIllustrate(String.format("missing field '%s'", key));
                RunTimeDataFactory.getResultInstance().addDefects(defects);
            }
        }
        return conditions;
    }

    /**
     * 求交集
     * @param expectKeys
     * @param actualKeys
     * @return
     */
    private Set<String> intersect(Set<String> expectKeys, Set<String> actualKeys) {
        Set<String> intersect = new HashSet<>(expectKeys);
        intersect.retainAll(actualKeys);
        return intersect;
    }

    private void compared(Object expect, Object actual) throws IllegalAccessException {
        ComparedUtil.notSureAboutComparison(expect, actual);
    }

}
