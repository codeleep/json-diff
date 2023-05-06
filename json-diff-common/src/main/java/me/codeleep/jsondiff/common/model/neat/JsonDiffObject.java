package me.codeleep.jsondiff.common.model.neat;

import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2023/04/16 21:02
 * @description: 对象
 */
public interface JsonDiffObject extends JsonDiff{

    /**
     * 获取key对应的值
     * @param key key
     * @return 值
     */
    Object get(String key);


    /**
     * 获取key集合
     * @return key集合
     */
    Set<String> keySet();


}
