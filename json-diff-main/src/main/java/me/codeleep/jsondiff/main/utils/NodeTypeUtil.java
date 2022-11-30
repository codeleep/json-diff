package me.codeleep.jsondiff.main.utils;

import me.codeleep.jsondiff.main.array.MultidimensionalArrayHandle;
import me.codeleep.jsondiff.main.array.ObjectArrayHandle;
import me.codeleep.jsondiff.main.array.SimpleArrayHandle;
import me.codeleep.jsondiff.spi.model.array.AbstractDiffJsonArray;
import me.codeleep.jsondiff.spi.model.object.AbstractDiffJsonObject;

/**
 * @author: codeleep
 * @createTime: 2022/11/26 21:52
 * @description: 类型判断器
 */
public class NodeTypeUtil {




    /**
     * 数组的元素 1. 基本类型  2. 对象  3. 数组
     * 暂时不支持元素是多种类型的数据
     * @param item 判断的元素类型
     * @return 返回使用的类型
     */
    public static Class<?> parseItemClass(Object item) {
        if(isPrimitiveType(item)) {
            return SimpleArrayHandle.class;
        }
        // 多维数组
        if (item instanceof AbstractDiffJsonArray) {
            return MultidimensionalArrayHandle.class;
        }
        // 对象数组
        if (item instanceof AbstractDiffJsonObject) {
            return ObjectArrayHandle.class;
        }
        return SimpleArrayHandle.class;
    }


    /**
     * 判断当前对象是否为json数据格式中的基本类型
     * @param obj
     * @return
     */
    public static boolean isPrimitiveType(Object obj){

        if(obj == null){
            return true;
        }

        if(obj instanceof AbstractDiffJsonObject || obj instanceof AbstractDiffJsonArray){
            return false;
        }

        if (String.class.isAssignableFrom(obj.getClass())) {
            return true;
        }

        try {
            return ((Class<?>)obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }



}
