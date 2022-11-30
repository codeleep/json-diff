package me.codeleep.jsondiff.spi.handle.object;

import me.codeleep.jsondiff.common.model.DiffProcessResultStack;
import me.codeleep.jsondiff.spi.handle.Handle;
import me.codeleep.jsondiff.spi.model.object.AbstractDiffJsonObject;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 19:26
 * @description: json对象处理器接口
 */
public interface ObjectHandle extends Handle {

    /**
     * 处理json对象
     * @param expectObject 期望json对象
     * @param actualObject 实际json对象
     * @return 对比结果
     */
    DiffProcessResultStack handleObject(AbstractDiffJsonObject expectObject, AbstractDiffJsonObject actualObject);

}
