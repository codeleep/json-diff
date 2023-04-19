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
    private Class<? extends JsonNeat> objectJsonNeat = ComplexObjectJsonNeat.class;

    /**
     * 数组默认比较器
     */
    private Class<? extends JsonNeat> arrayJsonNeat = ComplexArrayJsonNeat.class;

    /**
     * 基本类型默认比较器
     */
    private Class<? extends JsonNeat> primitiveJsonNeat = PrimitiveTypeJsonNeat.class;

    /**
     * 指定的path使用自定义比较器
     * key: 与ignorePath格式一致
     * value: 继承 AbstractArrayJsonNeat,AbstractObjectJsonNeat,AbstractPrimitiveJsonNeat. 并且实现对应格式接口的字节码
     */
    private final Map<String, Class<? extends JsonNeat>> customComparator = new HashMap<>();



    public Class<? extends JsonNeat> getObjectJsonNeat(boolean defaultNeat) {
        if (defaultNeat) {
            return ComplexObjectJsonNeat.class;
        }
        return objectJsonNeat;
    }

    public JsonNeat getObjectJsonNeatInstance(boolean defaultNeat) {
        return ClassUtil.getClassNameInstance(getObjectJsonNeat(defaultNeat));
    }

    public void setObjectJsonNeat(Class<? extends JsonNeat> objectJsonNeat) {
        this.objectJsonNeat = objectJsonNeat;
    }

    public Class<? extends JsonNeat> getArrayJsonNeat(boolean defaultNeat) {
        if (defaultNeat) {
            return ComplexArrayJsonNeat.class;
        }
        return arrayJsonNeat;
    }

    public JsonNeat getArrayJsonNeatInstance(boolean defaultNeat) {
        return ClassUtil.getClassNameInstance(getArrayJsonNeat(defaultNeat));
    }

    public void setArrayJsonNeat(Class<? extends JsonNeat> arrayJsonNeat) {
        this.arrayJsonNeat = arrayJsonNeat;
    }

    public Class<? extends JsonNeat> getPrimitiveJsonNeat(boolean defaultNeat) {
        if (defaultNeat) {
            return PrimitiveTypeJsonNeat.class;
        }
        return primitiveJsonNeat;
    }

    public JsonNeat getPrimitiveJsonNeatInstance(boolean defaultNeat) {
        return ClassUtil.getClassNameInstance(getPrimitiveJsonNeat(defaultNeat));
    }

    public void setPrimitiveJsonNeat(Class<? extends JsonNeat> primitiveJsonNeat) {
        this.primitiveJsonNeat = primitiveJsonNeat;
    }

    public Class<? extends JsonNeat> getCustomComparator(String path) {
        return customComparator.get(path);
    }

    public void addCustomComparator(String path, Class<? extends JsonNeat> customComparator) {
        this.customComparator.put(path, customComparator);
    }

}
