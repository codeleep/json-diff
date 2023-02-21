package me.codeleep.jsondiff.utils;

/**
 * @author: codeleep
 * @createTime: 2023/02/20 19:57
 * @description:
 */
public class ClassUtil {

    public static boolean isSameClass(Object obj1, Object obj2) {
        if (obj1 != null && obj2 != null) {
            return obj1.getClass().equals(obj2.getClass());
        }
        return obj1 == null && obj2 == null;
    }

}
