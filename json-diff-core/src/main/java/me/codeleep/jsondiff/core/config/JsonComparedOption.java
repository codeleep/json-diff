package me.codeleep.jsondiff.core.config;

import me.codeleep.jsondiff.core.handle.AbstractHandleFactory;
import me.codeleep.jsondiff.core.handle.HandleFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 17:00
 * @description: 对比配置
 */
public class JsonComparedOption {

    /**
     * 忽略数组顺序
     */
    private Boolean ignoreOrder = false;

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
     * 默认的比较器工厂
     */
    private HandleFactory jsonNeatFactory = new AbstractHandleFactory();


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

    public HandleFactory getJsonNeatFactory() {
        return jsonNeatFactory;
    }

    public void setJsonNeatFactory(HandleFactory jsonNeatFactory) {
        this.jsonNeatFactory = jsonNeatFactory;
    }
}
