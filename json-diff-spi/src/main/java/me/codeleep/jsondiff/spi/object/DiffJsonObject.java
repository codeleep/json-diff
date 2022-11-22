package me.codeleep.jsondiff.spi.object;

import me.codeleep.jsondiff.spi.array.DiffJsonArray;

import java.util.Map;
import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2022/11/14 15:59
 * @description: 抽象Json对象
 */
public interface DiffJsonObject extends Map {

    /**
     * 是否存在某个key
     * @param key key
     * @return 是否存在
     */
    boolean containsKey(String key);


    /**
     * 获取类型是 DiffJsonArray 的元素
     * @param key key
     * @return 返回转化后的值
     */
    DiffJsonArray getDiffJsonArray(String key);


    /**
     * 获取类型是 DiffJsonObject 的元素
     * @param key
     * @return
     */
    DiffJsonObject getDiffJsonObject(String key);


    /**
     * 获取元素
     * @param key key
     * @return 指定key的元素
     */
    Object get(String key);

    String getString(String key);

    Long getLong(String key);

    Boolean getBoolean(String key);

}
