package me.codeleep.jsondiff.common.model;

import me.codeleep.jsondiff.common.model.neat.JsonDiff;

import java.util.HashMap;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 19:26
 * @description: 表示每一个差异说明
 */
public class Defects {

    /**
     * 期望结果
     */
    private Object expect;

    /**
     * 实际结果
     */
    private Object actual;

    /**
     * 索引地址
     */
    private TravelPath travelPath;

    /**
     * 说明
     */
    private String illustrate;

    /**
     * 附加数据
     */
    private HashMap<String, Object> additionalData;


    public Defects setExpect(Object expect) {
        if (expect instanceof JsonDiff) {
            this.expect = ((JsonDiff)expect).format();
        } else {
            this.expect = expect;
        }
        return this;
    }

    public Defects setActual(Object actual) {
        if (actual instanceof JsonDiff) {
            this.actual = ((JsonDiff)actual).format();
        } else {
            this.actual = actual;
        }
        return this;
    }

    public TravelPath getTravelPath() {
        return travelPath;
    }

    public Defects setTravelPath(TravelPath travelPath) {
        this.travelPath = travelPath;
        return this;
    }

    public Defects setIllustrate(String illustrate) {
        this.illustrate = illustrate;
        return this;
    }

    public Defects setIllustrateTemplate(String format, String ...args) {
        this.illustrate = String.format(format, args);
        return this;
    }

    public Object getExpect() {
        return expect;
    }

    public Object getActual() {
        return actual;
    }

    public String getIllustrate() {
        return illustrate;
    }

    public HashMap<String, Object> getAdditionalData() {
        return additionalData;
    }

    public Object getAdditionalData(String key) {
        if (additionalData == null) {
            return null;
        }
        return additionalData.get(key);
    }

    public void setAdditionalData(HashMap<String, Object> additionalData) {
        this.additionalData = additionalData;
    }

    public void pushAdditionalData(String key, Object data) {
        if (this.additionalData == null) {
            this.additionalData = new HashMap<>();
        }
        this.additionalData.put(key, data);
    }
}
