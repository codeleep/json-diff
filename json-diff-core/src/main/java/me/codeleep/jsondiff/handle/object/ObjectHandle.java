package me.codeleep.jsondiff.handle.object;

import me.codeleep.jsondiff.model.json.DiffJsonObject;
import me.codeleep.jsondiff.handle.Handle;
import me.codeleep.jsondiff.model.JsonCompareResult;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 19:26
 * @description: 对象处理
 */
public interface ObjectHandle extends Handle {

    JsonCompareResult handle(DiffJsonObject expectObject, DiffJsonObject actualObject);

}
