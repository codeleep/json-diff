package me.codeleep.jsondiff.handle.array;


import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.handle.Handle;
import me.codeleep.jsondiff.model.JsonCompareResult;

/**
 * @author: codeleep
 * @createTime: 2022/07/17 16:31
 * @description: 处理JSONArray的对比
 */
public interface ArrayHandle extends Handle {

    JsonCompareResult handle(JSONArray expectArray, JSONArray actualArray);

}
