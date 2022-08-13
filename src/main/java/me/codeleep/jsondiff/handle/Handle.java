package me.codeleep.jsondiff.handle;

import me.codeleep.jsondiff.model.JsonCompareResult;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 19:36
 * @description:
 */
public interface Handle {

    JsonCompareResult handle();

    JsonCompareResult getResult();

}
