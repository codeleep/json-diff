package me.codeleep.jsondiff.common.model.neat;

import me.codeleep.jsondiff.common.model.JsonCompareResult;

/**
 * @author: codeleep
 * @createTime: 2023/03/10 23:26
 * @description: 基础类型
 */
public interface PrimitiveJsonNeat extends JsonNeat{

    /**
     * 比较数组
     * @param expect 基础类型对象
     * @param actual 基础类型对象
     * @return 返回比较结果
     */
    JsonCompareResult detectDiff(Object expect, Object actual);

}
