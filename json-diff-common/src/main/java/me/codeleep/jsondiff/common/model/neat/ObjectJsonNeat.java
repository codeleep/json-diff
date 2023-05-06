package me.codeleep.jsondiff.common.model.neat;

import me.codeleep.jsondiff.common.model.JsonCompareResult;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:14
 * @description:
 */
public interface ObjectJsonNeat extends JsonNeat{

    /**
     * 比较对象
     * @param expect 期望的json对象
     * @param actual 实际的json对象
     * @return 返回比较结果
     * @throws IllegalAccessException 发生异常直接抛出
     */
    JsonCompareResult detectDiff(JsonDiffObject expect, JsonDiffObject actual);

}
