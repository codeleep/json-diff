package me.codeleep.jsondiff.core.handle.object;

import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.MappingKey;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;
import me.codeleep.jsondiff.common.model.neat.JsonNeat;
import me.codeleep.jsondiff.common.utils.PathUtil;
import me.codeleep.jsondiff.core.utils.ClassUtil;
import me.codeleep.jsondiff.core.utils.RunTimeDataFactory;

import java.util.*;
import java.util.stream.Collectors;

import static me.codeleep.jsondiff.common.model.Constant.DATA_TYPE_INCONSISTENT;
import static me.codeleep.jsondiff.common.model.Constant.SEPARATE_KEY;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:21
 * @description: 比较复杂的json对象
 */
public class ComplexObjectJsonNeat extends AbstractObjectJsonNeat<JsonDiffObject> {

    /**
     * 应比较集合
     * key: actual 的 key
     * value: expect 的 key
     */
    private final List<MappingKey> keyMap = new ArrayList<>();

    public ComplexObjectJsonNeat(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
        super(travelPath, expect, actual);
    }

    /**
     * 计算出两个应该比较的key集合
     * @param expectKeys
     * @param actualKeys
     * @return
     */
    private void keySetConversion(Set<String> expectKeys, Set<String> actualKeys) {
        // 应该遍历的Key
        HashSet<String> neatExpectKeys = new HashSet<>();
        HashSet<String> neatActualKeys = new HashSet<>();
        // 字段映射
        Map<String, String> mapping = RunTimeDataFactory.getOptionInstance().getMapping();
        for (Map.Entry<String, String> entry: mapping.entrySet()) {
            if (actualKeys.contains(entry.getKey()) && expectKeys.contains(entry.getValue())) {
                neatActualKeys.add(entry.getKey());
                neatExpectKeys.add(entry.getValue());
                keyMap.add(new MappingKey(entry.getValue(), entry.getKey()));
            }
        }
        // 忽略的key
        HashSet<String> ignoreKey = RunTimeDataFactory.getOptionInstance().getIgnoreKey();
        // 遍历期望
        for (String key: expectKeys) {
            if (ignoreKey.contains(key) || neatExpectKeys.contains(key)) {
                continue;
            }
            neatExpectKeys.add(key);
            if (actualKeys.contains(key)) {
                neatActualKeys.add(key);
            }
            keyMap.add(new MappingKey(key, actualKeys.contains(key) ? key : null));
        }
        for (String key: actualKeys) {
            if (ignoreKey.contains(key) || neatActualKeys.contains(key)) {
                continue;
            }
            neatActualKeys.add(key);
            keyMap.add(new MappingKey(expectKeys.contains(key) ? key : null, key));
        }
        // 移除忽略的Path
        HashSet<String> ignorePath = RunTimeDataFactory.getOptionInstance().getIgnorePath();
        List<MappingKey>  mappingKeys = keyMap.stream().filter(mappingKey -> {
            String actualKey = mappingKey.getActualKey();
            if (actualKey != null) {
                String actualTravelPath = PathUtil.getObjectPath(travelPath.getActualTravelPath()) + mappingKey.getActualKey();
                String abstractActualTravelPath = PathUtil.getObjectPath(travelPath.getAbstractTravelPath()) + mappingKey.getActualKey();
                if (ignorePath.contains(actualTravelPath) || ignorePath.contains(abstractActualTravelPath)) {
                    return false;
                }
            }
            String expectKey = mappingKey.getExpectKey();
            if (expectKey != null) {
                String expectTravelPath = PathUtil.getObjectPath(travelPath.getExpectTravelPath()) + mappingKey.getExpectKey();
                String abstractExpectTravelPath = PathUtil.getObjectPath(travelPath.getAbstractTravelPath()) + mappingKey.getExpectKey();
                if (ignorePath.contains(expectTravelPath) || ignorePath.contains(abstractExpectTravelPath)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
        // 清理
        keyMap.clear();
        keyMap.addAll(mappingKeys);
    }


    @Override
    protected JsonCompareResult diff1() {
        // 计算出; 应该比较的key集合
        keySetConversion(expect.keySet(), actual.keySet());
        // 遍历比较
        for (MappingKey mappingKey : keyMap) {
            // 未找到另外一个key。只存存在其中一个为null情况
            if (mappingKey.getExpectKey() == null || mappingKey.getActualKey() == null) {
                Defects defects = new Defects()
                        .setActual(actual.get(mappingKey.getActualKey()))
                        .setExpect(expect.get(mappingKey.getExpectKey()))
                        .setTravelPath(new TravelPath(travelPath, mappingKey))
                        .setIllustrateTemplate(SEPARATE_KEY, mappingKey.getExpectKey(), mappingKey.getActualKey());
                result.addDefects(defects);
                continue;
            }

            JsonDiff expectDiffJson = expect.get(mappingKey.getExpectKey());
            JsonDiff actualDiffJson = actual.get(mappingKey.getActualKey());
            TravelPath nextTravelPath = new TravelPath(travelPath, mappingKey);
            // 判断类型, 根据类型去实例化JsonNeat。
            JsonNeat<? extends JsonDiff> jsonNeat = RunTimeDataFactory.getOptionInstance().getJsonNeatFactory().generate(nextTravelPath, expectDiffJson, actualDiffJson);
            if (jsonNeat == null) {
                Defects defects = new Defects()
                        .setActual(actualDiffJson)
                        .setExpect(expectDiffJson)
                        .setTravelPath(nextTravelPath)
                        .setIllustrateTemplate(DATA_TYPE_INCONSISTENT, ClassUtil.getClassName(expectDiffJson), ClassUtil.getClassName(actualDiffJson));
                result.addDefects(defects);
                continue;
            }
            // 比较非基础类型
            JsonCompareResult diff = jsonNeat.diff();
            // 将结果合并
            if (!diff.isMatch()) {
                result.mergeDefects(diff.getDefectsList());
            }
        }
        return result;
    }

}
