package me.codeleep.jsondiff.model;

import me.codeleep.jsondiff.spi.function.Function;

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
    private List<String> ignorePath;

    /**
     * 忽略的key。actual中有的字段，但expect没有的，会被忽略掉
     */
    private List<String> ignoreKey;

    /**
     * ignoreOrder=true时，数组是元素对象时, 指定根据哪些key联系对象
     */
    private Function<String, Stack<String>> keyFunction;


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

    public JsonComparedOption setIgnorePath(List<String> ignorePath) {
        this.ignorePath = ignorePath;
        return this;
    }

    public JsonComparedOption setIgnoreKey(List<String> ignoreKey) {
        this.ignoreKey = ignoreKey;
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

    public List<String> getIgnorePath() {
        if (ignorePath == null) {
            ignorePath = new ArrayList<>();
        }
        return ignorePath;
    }

    public List<String> getIgnoreKey() {
        if (ignoreKey == null) {
            ignoreKey = new ArrayList<>();
        }
        return ignoreKey;
    }
    public Function<String, Stack<String>> getKeyFunction() {
        return keyFunction;
    }

    public JsonComparedOption setKeyFunction(Function<String, Stack<String>> keyFunction) {
        this.keyFunction = keyFunction;
        return this;
    }
}
