package me.codeleep.jsondiff.handle.array;


import me.codeleep.jsondiff.model.json.DiffJsonArray;
import me.codeleep.jsondiff.handle.Handle;
import me.codeleep.jsondiff.model.JsonCompareResult;

/**
 * @author: codeleep
 * @createTime: 2022/07/17 16:31
 * @description: 处理DiffJsonArray的对比
 */
public interface ArrayHandle extends Handle {

    JsonCompareResult handle(DiffJsonArray expectArray, DiffJsonArray actualArray);

}
