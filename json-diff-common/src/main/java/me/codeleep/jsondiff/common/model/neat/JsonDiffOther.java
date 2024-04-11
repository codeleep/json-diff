package me.codeleep.jsondiff.common.model.neat;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 上午11:06
 * @description: other
 */
public interface JsonDiffOther extends JsonDiff{

    Object getOther();

    boolean isEquals(JsonDiffOther jsonDiffOther);

}
