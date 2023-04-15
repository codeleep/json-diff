package me.codeleep.jsondiff.core.utils;

import me.codeleep.jsondiff.common.model.neat.JsonNeat;
import me.codeleep.jsondiff.core.handle.array.ComplexArrayJsonNeat;
import me.codeleep.jsondiff.core.handle.object.ComplexObjectJsonNeat;
import me.codeleep.jsondiff.core.handle.primitive.PrimitiveTypeJsonNeat;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: codeleep
 * @createTime: 2023/04/15 13:39
 * @description: 比较器工厂
 */
public class JsonNeatFactory {

    /**
     * 对象默认比较器
     */
    private static Class<? extends JsonNeat> objectJsonNeat = ComplexObjectJsonNeat.class;

    /**
     * 数组默认比较器
     */
    private static Class<? extends JsonNeat> arrayJsonNeat = ComplexArrayJsonNeat.class;

    /**
     * 基本类型默认比较器
     */
    private static Class<? extends JsonNeat> primitiveJsonNeat = PrimitiveTypeJsonNeat.class;

    /**
     * 指定的path使用自定义比较器
     * key: 与ignorePath格式一致
     * value: 继承 AbstractArrayJsonNeat,AbstractObjectJsonNeat,AbstractPrimitiveJsonNeat. 并且实现对应格式接口的字节码
     */
    private static final Map<String, Class<? extends JsonNeat>> customComparator = new HashMap<>();



    public static Class<? extends JsonNeat> getObjectJsonNeat(boolean defaultNeat) {
        if (defaultNeat) {
            return ComplexObjectJsonNeat.class;
        }
        return objectJsonNeat;
    }

    public static JsonNeat getObjectJsonNeatInstance(boolean defaultNeat) {
        return ClassUtil.getClassNameInstance(getObjectJsonNeat(defaultNeat));
    }

    public static void setObjectJsonNeat(Class<? extends JsonNeat> objectJsonNeat) {
        JsonNeatFactory.objectJsonNeat = objectJsonNeat;
    }

    public static Class<? extends JsonNeat> getArrayJsonNeat(boolean defaultNeat) {
        if (defaultNeat) {
            return ComplexArrayJsonNeat.class;
        }
        return arrayJsonNeat;
    }

    public static JsonNeat getArrayJsonNeatInstance(boolean defaultNeat) {
        return ClassUtil.getClassNameInstance(getArrayJsonNeat(defaultNeat));
    }

    public static void setArrayJsonNeat(Class<? extends JsonNeat> arrayJsonNeat) {
        JsonNeatFactory.arrayJsonNeat = arrayJsonNeat;
    }

    public static Class<? extends JsonNeat> getPrimitiveJsonNeat(boolean defaultNeat) {
        if (defaultNeat) {
            return PrimitiveTypeJsonNeat.class;
        }
        return primitiveJsonNeat;
    }

    public static JsonNeat getPrimitiveJsonNeatInstance(boolean defaultNeat) {
        return ClassUtil.getClassNameInstance(getPrimitiveJsonNeat(defaultNeat));
    }

    public static void setPrimitiveJsonNeat(Class<? extends JsonNeat> primitiveJsonNeat) {
        JsonNeatFactory.primitiveJsonNeat = primitiveJsonNeat;
    }

    public static Class<? extends JsonNeat> getCustomComparator(String path) {
        return customComparator.get(path);
    }

    public static void addCustomComparator(String path, Class<? extends JsonNeat> customComparator) {
        JsonNeatFactory.customComparator.put(path, customComparator);
    }

}
