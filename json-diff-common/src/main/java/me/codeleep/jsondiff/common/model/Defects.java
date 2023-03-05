package me.codeleep.jsondiff.common.model;

import me.codeleep.jsondiff.common.utils.PathUtil;

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


    public Defects setExpect(Object expect) {
        this.expect = expect;
        return this;
    }

    public Defects setActual(Object actual) {
        this.actual = actual;
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
}
