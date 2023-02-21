package me.codeleep.jsondiff.utils;

import me.codeleep.jsondiff.model.MappingKey;

/**
 * @author: codeleep
 * @createTime: 2023/02/20 17:39
 * @description: 路径工具类
 */
public class PathUtil {


    private static final String OBJECT_SING = ".";

    private static final String ARRAY_SING = "[]";


    public static String getObjectPath(String path, MappingKey mappingKey) {
        if (mappingKey.getActualKey() == null) {
            return path + OBJECT_SING + mappingKey.getExpectKey();
        }
        return path + OBJECT_SING + mappingKey.getActualKey();
    }

    public static String getIndexPath(String path, int index) {
        return path + ARRAY_SING;
    }


}
