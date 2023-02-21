package me.codeleep.jsondiff.common.model;

/**
 * @author: codeleep
 * @createTime: 2023/02/20 17:55
 * @description: 说明类型
 */
public enum IllustrateType {

    ELEMENT_INCONSISTENCY("元素不一致"),
    VALUE_INCONSISTENCY("内容不一致"),
    FIELD_NODE_FOUND("字段未找到"),
    ARRAY_LENGTH_INCONSISTENCY("数组长度不一致")
    ;

    private String doc;

    IllustrateType(String doc) {
        this.doc = doc;
    }

}
