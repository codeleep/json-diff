package me.codeleep.jsondiff.handle.object;

import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.model.MappingKey;
import me.codeleep.jsondiff.neat.JsonNeat;
import me.codeleep.jsondiff.utils.JsonCompareUtil;
import me.codeleep.jsondiff.utils.JsonDiffUtil;
import me.codeleep.jsondiff.utils.PathUtil;

import java.util.*;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:21
 * @description: 比较复杂的json对象
 */
public class ComplexObjectJsonNeat extends AbstractObjectJsonNeat {

    /**
     * 应比较集合
     * key: actual 的 key
     * value: expect 的 key
     */
    private final List<MappingKey> keyMap = new ArrayList<>();

    /**
     * 路径
     */
    private String path;

    /**
     * 当前节点比较结果
     */
    private final JsonCompareResult result = new JsonCompareResult();


    @Override
    public JsonCompareResult detectDiff(JSONObject expect, JSONObject actual) {
        preCheck(expect, actual);
        // 计算出; 应该比较的key集合
        keySetConversion(expect.keySet(), actual.keySet());
        // 遍历比较
        for (MappingKey mappingKey : keyMap) {
            JSONObject expectDiffJson = expect.getJSONObject(mappingKey.getExpectKey());
            JSONObject actualDiffJson = actual.getJSONObject(mappingKey.getActualKey());
            // 判断类型, 根据类型去实例化JsonNeat。
            JsonNeat jsonNeat = JsonDiffUtil.getJsonNeat(expectDiffJson, actualDiffJson);
            if (jsonNeat == null) {
                JsonCompareResult jsonCompareResult = JsonCompareUtil.handlePrimitiveType(expectDiffJson, actualDiffJson, PathUtil.getObjectPath(this.path, mappingKey));
                if (!jsonCompareResult.isMatch()) {
                    result.mergeDefects(jsonCompareResult.getDefectsList());
                }
                continue;
            }
            // 比较非基础类型
            JsonCompareResult diff = jsonNeat.diff(expect, actual, PathUtil.getObjectPath(path, mappingKey));
            // 将结果合并
            if (!diff.isMatch()) {
                result.mergeDefects(diff.getDefectsList());
            }
        }
        return result;
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
            neatActualKeys.add(entry.getKey());
            neatExpectKeys.add(entry.getValue());
            keyMap.add(new MappingKey(entry.getKey(), entry.getValue()));
        }
        // 忽略的key
        HashSet<String> ignoreKey = RunTimeDataFactory.getOptionInstance().getIgnoreKey();
        // 遍历期望
        for (String key: expectKeys) {
            if (ignoreKey.contains(key) || neatExpectKeys.contains(key)) {
                continue;
            }
            neatExpectKeys.add(key);
            keyMap.add(new MappingKey(key, actualKeys.contains(key) ? key : null));
        }
        for (String key: actualKeys) {
            if (ignoreKey.contains(key) || neatActualKeys.contains(key)) {
                continue;
            }
            neatActualKeys.add(key);
            keyMap.add(new MappingKey(expectKeys.contains(key) ? key : null, key));
        }
    }

    @Override
    public JsonCompareResult diff(JSONObject expect, JSONObject actual, String path) {
        this.path = path;
        return detectDiff(expect, actual);
    }

}
