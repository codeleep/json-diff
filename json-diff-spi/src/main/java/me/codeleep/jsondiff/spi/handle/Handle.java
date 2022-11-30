package me.codeleep.jsondiff.spi.handle;

import me.codeleep.jsondiff.common.model.DiffProcessResultStack;

/**
 * @author: codeleep
 * @createTime: 2022/11/26 22:07
 * @description: 顶层处理类
 */
public interface Handle {

    /**
     * 顶层入口
     * @param expect 期望对象
     * @param actual 实际对象
     * @return 对比结果
     */
    DiffProcessResultStack handle(Object expect, Object actual);

}
