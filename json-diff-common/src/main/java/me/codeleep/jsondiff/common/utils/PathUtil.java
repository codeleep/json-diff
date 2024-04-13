package me.codeleep.jsondiff.common.utils;


/**
 * @author: codeleep
 * @createTime: 2023/02/20 17:39
 * @description: 路径工具类
 */
public class PathUtil {

    private static final String OBJECT_SING = ".";

    private static final String ARRAY_SING_LEFT = "[";
    private static final String ARRAY_SING_RIGHT = "]";


    public static String getAbstractIndexPath(String index) {
        return ARRAY_SING_LEFT + "*" + ARRAY_SING_RIGHT;
    }

    public static String getIndexPath(String index) {
        return ARRAY_SING_LEFT + index + ARRAY_SING_RIGHT;
    }

    public static String getObjectPath(String parentPath) {
        return parentPath + OBJECT_SING;
    }

}
