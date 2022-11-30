package me.codeleep.jsondiff.spi;

import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.spi.model.array.DiffJsonArray;
import me.codeleep.jsondiff.spi.model.object.DiffJsonObject;

/**
 * @author: codeleep
 * @createTime: 2022/11/26 10:55
 * @description: 比较接口
 */
public interface JsonDifference {

    /**
     * 比较对象
     * @param expect 期望的json对象
     * @param actual 实际的json对象
     * @return 返回比较结果
     * @throws IllegalAccessException 发生异常直接抛出
     */
    JsonCompareResult detectDiff(DiffJsonObject expect, DiffJsonObject actual) throws IllegalAccessException;

    /**
     * 比较数组
     * @param expect 期望的json对象
     * @param actual 实际的json对象
     * @return 返回比较结果
     * @throws IllegalAccessException 发生异常直接抛出
     */
    JsonCompareResult detectDiff(DiffJsonArray expect, DiffJsonArray actual) throws IllegalAccessException;

}
