package me.codeleep.jsondiff.core.utils;

import static me.codeleep.jsondiff.common.model.Constant.NULL;

/**
 * @author: codeleep
 * @createTime: 2023/02/20 19:57
 * @description:
 */
public class ClassUtil {

    /**
     * 判断两个 class 是否相同; 当两个都为null时表示相同
     * @param obj1
     * @param obj2
     * @return 当obj1=obj2返回true。
     */
    public static boolean isSameClass(Object obj1, Object obj2) {
        if (obj1 != null && obj2 != null) {
            return obj1.getClass().equals(obj2.getClass());
        }
        return obj1 == null && obj2 == null;
    }


    /**
     * 获取className
     * @param obj
     * @return
     */
    public static String getClassName(Object obj) {
        if (obj == null) {
            return NULL;
        }
        return obj.getClass().getName();
    }
}
