package me.codeleep.jsondiff.common.model;

import me.codeleep.jsondiff.common.model.neat.JsonNeat;

import java.util.*;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 17:00
 * @description: 对比配置
 */
public class JsonComparedOption {

    /**
     * 忽略数组顺序
     */
    private boolean ignoreOrder;

    /**
     * key 是 actual
     * value 是 expect 映射
     */
    private Map<String, String> mapping;

    /**
     * 忽略的path. 以 . 来区分json层级; 会精准匹配路径
     */
    private HashSet<String> ignorePath;

    /**
     * 忽略的key。不会比较该key
     */
    private HashSet<String> ignoreKey;

    /**
     * 指定的path使用自定义比较器
     * key: 与ignorePath格式一致
     * value: 继承 AbstractArrayJsonNeat,AbstractObjectJsonNeat,AbstractPrimitiveJsonNeat. 并且实现对应格式接口的字节码
     */
    private Map<String, Class<? extends JsonNeat>> customComparator = new HashMap<>();


    public JsonComparedOption() {
    }

    public JsonComparedOption setIgnoreOrder(boolean ignoreOrder) {
        this.ignoreOrder = ignoreOrder;
        return this;
    }


    public JsonComparedOption setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
        return this;
    }

    public JsonComparedOption setIgnorePath(HashSet<String> ignorePath) {
        this.ignorePath = ignorePath;
        return this;
    }

    public JsonComparedOption setIgnoreKey(HashSet<String> ignoreKey) {
        this.ignoreKey = ignoreKey;
        return this;
    }

    public JsonComparedOption setCustomComparator(Map<String, Class<? extends JsonNeat>> customComparator ) {
        this.customComparator = customComparator;
        return this;
    }

    public boolean isIgnoreOrder() {
        return ignoreOrder;
    }

    public Map<String, String> getMapping() {
        if (mapping == null) {
            mapping = new HashMap<>();
        }
        return mapping;
    }

    public HashSet<String> getIgnorePath() {
        if (ignorePath == null) {
            ignorePath = new HashSet<>();
        }
        return ignorePath;
    }

    public HashSet<String> getIgnoreKey() {
        if (ignoreKey == null) {
            ignoreKey = new HashSet<>();
        }
        return ignoreKey;
    }


    public Map<String, Class<? extends JsonNeat>> getCustomComparator() {
        if (customComparator == null) {
            customComparator = new HashMap<String, Class<? extends JsonNeat>>();
        }
        return customComparator;
    }
}
