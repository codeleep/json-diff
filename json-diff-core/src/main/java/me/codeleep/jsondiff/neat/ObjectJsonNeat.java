package me.codeleep.jsondiff.neat;

import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.spi.model.object.DiffJsonObject;

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
    JsonCompareResult detectDiff(JSONObject expect, JSONObject actual);

}
