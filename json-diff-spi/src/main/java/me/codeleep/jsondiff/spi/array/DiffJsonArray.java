package me.codeleep.jsondiff.spi.array;


import me.codeleep.jsondiff.spi.object.DiffJsonObject;

import java.util.List;

/**
 * @author: codeleep
 * @createTime: 2022/11/14 15:59
 * @description: 抽象Json对象
 */
public interface DiffJsonArray extends List {

    /**
     * 根据下标获取DiffJsonArray对象
     * @param index 下标
     * @return 返回对应下标的DiffJsonArray
     */
    DiffJsonArray getDiffJsonArray(int index);

    /**
     * 根据下标获取DiffJsonObject对象
     * @param index 下标
     * @return 返回对应下标的DiffJsonObject
     */
    DiffJsonObject getDiffJsonObject(int index);

    /**
     * 转化为数组
     * @return 返回对象数组
     */
    Object[] toArray();

}
