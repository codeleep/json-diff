package me.codeleep.jsondiff.core.utils;

import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;

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
     * 判断当前对象是否为json数据格式中的基本类型
     * @param obj 判断的对象
     * @return 是否为基本类型
     */
    public static boolean isPrimitiveType(Object obj){
        if(obj == null){
            return true;
        }

        if(obj instanceof JsonDiffArray || obj instanceof JsonDiffObject){
            return false;
        }

        if (String.class.isAssignableFrom(obj.getClass())) {
            return true;
        }
        if (obj instanceof Number) {
            return true;
        }
        try {
            return ((Class<?>)obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
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

    public static <T> T getClassNameInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new JsonDiffException(String.format("无法实例化: %s", clazz), e);
        }
    }
}
