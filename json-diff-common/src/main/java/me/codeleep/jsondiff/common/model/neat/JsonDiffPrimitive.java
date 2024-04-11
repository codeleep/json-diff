package me.codeleep.jsondiff.common.model.neat;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 上午9:50
 * @description: 基础数据
 */
public interface JsonDiffPrimitive extends JsonDiff {

    boolean isEquals(JsonDiff jsonDiff);

}
