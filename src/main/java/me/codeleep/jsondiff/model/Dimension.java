package me.codeleep.jsondiff.model;

/**
 * @author: codeleep
 * @createTime: 2022/07/31 09:17
 * @description: 判断使用处理器的维度
 */
public class Dimension {

    private Boolean isPrimitive;
    private Boolean isSingleType;

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public Dimension setPrimitive(boolean primitive) {
        isPrimitive = primitive;
        return this;
    }

    public boolean isSingleType() {
        return isSingleType;
    }

    public Dimension setSingleType(boolean singleType) {
        isSingleType = singleType;
        return this;
    }
}
