package me.codeleep.jsondiff.spi.handle.array;


import me.codeleep.jsondiff.common.model.DiffProcessResultStack;
import me.codeleep.jsondiff.spi.handle.Handle;
import me.codeleep.jsondiff.spi.model.array.AbstractDiffJsonArray;

/**
 * @author: codeleep
 * @createTime: 2022/07/17 16:31
 * @description: json 数组处理器接口
 */
public interface ArrayHandle extends Handle {

    /**
     * 处理json对象
     * @param expectArray 期望json对象
     * @param actualArray 实际json对象
     * @return 返回对比结果
     */
    DiffProcessResultStack handleArray(AbstractDiffJsonArray expectArray, AbstractDiffJsonArray actualArray);

}
