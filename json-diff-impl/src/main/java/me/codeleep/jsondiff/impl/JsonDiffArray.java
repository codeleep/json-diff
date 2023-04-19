package me.codeleep.jsondiff.impl;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:02
 * @description: 数组
 */
public interface JsonDiffArray extends JsonDiff{

    /**
     * 获取key集合
     * @return key集合
     */
    int size();



    /**
     * 获取key对应的值
     * @param index 索引
     * @return 值
     */
    Object get(int index);

}
