package me.codeleep.jsondiff.common.model.neat;

import me.codeleep.jsondiff.common.model.JsonCompareResult;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:14
 * @description:
 */
public interface ArrayJsonNeat extends JsonNeat{

    /**
     * 比较数组
     * @param expect 期望的json对象
     * @param actual 实际的json对象
     * @return 返回比较结果
     */
    JsonCompareResult detectDiff(JsonDiffArray expect, JsonDiffArray actual);

    // 忽略顺序的比较
    JsonCompareResult ignoreOrder(JsonDiffArray expect, JsonDiffArray actual);

    // 保持顺序比较
    JsonCompareResult keepOrder(JsonDiffArray expect, JsonDiffArray actual);

}
