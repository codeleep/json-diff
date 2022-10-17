package me.codeleep.jsondiff.handle.object;

import me.codeleep.jsondiff.model.json.DiffJsonObject;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.model.Defects;
import me.codeleep.jsondiff.utils.ComparedUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 19:19
 * @description: 简单json对象对比
 */
public class SimpleObjectHandle extends AbstractObjectHandle {
    @Override
    protected void doHandle(DiffJsonObject expectObject, DiffJsonObject actualObject) {
        Set<String> expectKeys = expectObject.keySet();
        Set<String> actualKeys = actualObject.keySet();

        // 求两种集合的差集
        //Set<String> conditionsActual = conditionsActual(expectKeys, actualKeys);
        //Set<String> conditionsExpect = conditionsExpect(expectKeys, actualKeys);

        // 对比交集
        Set<String> intersect = intersect(expectKeys, actualKeys);

        // 遍历 交集 - 忽略的字段 - 映射的字段
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

        // 处理除开交集和映射字段之外的字段
        Set<String> actualLegacyFields = legacyFields(actualKeys, intersect);
        Set<String> expectLegacyFields = legacyFields(expectKeys, intersect);

        for (String key: actualLegacyFields) {
            Defects defects = new Defects()
                    .setActual(key)
                    .setExpect(null)
                    .setIndexPath(getCurrentPath())
                    .setIllustrate(String.format("real excess field '%s'", key));
            RunTimeDataFactory.getResultInstance().addDefects(defects);
        }

        for (String key: expectLegacyFields) {
            Defects defects = new Defects()
                    .setActual(key)
                    .setExpect(null)
                    .setIndexPath(getCurrentPath())
                    .setIllustrate(String.format("expect missing fields '%s'", key));
            RunTimeDataFactory.getResultInstance().addDefects(defects);
        }

    }

    /**
     * 求 集合 - 交集 - 忽略的字段 - 映射的字段
     * @param keys
     * @return
     */
    private Set<String> legacyFields(Set<String> keys, Set<String> intersect) {
        Set<String> tempKeys = new HashSet<>(keys);
        tempKeys.removeAll(intersect);
        tempKeys.removeAll(new HashSet<>(RunTimeDataFactory.getOptionInstance().getIgnoreKey()));
        tempKeys.removeAll(RunTimeDataFactory.getOptionInstance().getMapping().keySet());
        tempKeys.removeAll(new HashSet<>(RunTimeDataFactory.getOptionInstance().getMapping().values()));
        return tempKeys;
    }


    /**
     * 求 交集 - 忽略的字段 - 映射的字段
     * @param expectKeys
     * @param actualKeys
     * @return
     */
    private Set<String> intersect(Set<String> expectKeys, Set<String> actualKeys) {
        Set<String> intersect = new HashSet<>(expectKeys);
        intersect.retainAll(actualKeys);
        intersect.removeAll(new HashSet<>(RunTimeDataFactory.getOptionInstance().getIgnoreKey()));
        intersect.removeAll(RunTimeDataFactory.getOptionInstance().getMapping().keySet());
        intersect.removeAll(new HashSet<>(RunTimeDataFactory.getOptionInstance().getMapping().values()));
        return intersect;
    }

    private void compared(Object expect, Object actual) throws IllegalAccessException {
        ComparedUtil.notSureAboutComparison(expect, actual);
    }

}
