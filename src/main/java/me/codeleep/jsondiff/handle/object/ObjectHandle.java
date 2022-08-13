package me.codeleep.jsondiff.handle.object;

import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.handle.Handle;
import me.codeleep.jsondiff.model.JsonCompareResult;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 19:26
 * @description: 对象处理
 */
public interface ObjectHandle extends Handle {

    JsonCompareResult handle(JSONObject expectObject, JSONObject actualObject);

}
