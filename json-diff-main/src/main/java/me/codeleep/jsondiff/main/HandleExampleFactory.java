package me.codeleep.jsondiff.main;


import me.codeleep.jsondiff.common.model.DiffProcessResultStack;
import me.codeleep.jsondiff.main.array.IntricacyArrayHandle;
import me.codeleep.jsondiff.main.array.MultidimensionalArrayHandle;
import me.codeleep.jsondiff.main.array.ObjectArrayHandle;
import me.codeleep.jsondiff.main.array.SimpleArrayHandle;
import me.codeleep.jsondiff.main.object.SimpleObjectHandle;
import me.codeleep.jsondiff.spi.handle.Handle;
import me.codeleep.jsondiff.spi.handle.array.AbstractArrayHandle;
import me.codeleep.jsondiff.spi.handle.object.AbstractObjectHandle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: codeleep
 * @createTime: 2022/07/31 00:00
 * @description: 获取处理实例的工厂
 */
public class HandleExampleFactory {

    /**
     * 处理对象的实例桶
     */
    private final static Map<Class<?>, Handle> bucket = new HashMap<>();

    static {
        bucket.put(SimpleArrayHandle.class, new SimpleArrayHandle());
        bucket.put(SimpleObjectHandle.class, new SimpleObjectHandle());
        bucket.put(IntricacyArrayHandle.class, new IntricacyArrayHandle());
        bucket.put(ObjectArrayHandle.class, new ObjectArrayHandle());
        bucket.put(MultidimensionalArrayHandle.class, new MultidimensionalArrayHandle());
    }

    public static Handle getHandle(Class<?> handle) {
        if (!AbstractArrayHandle.class.isAssignableFrom(handle) && !AbstractObjectHandle.class.isAssignableFrom(handle) ) {
            return bucket.get(AbstractObjectHandle.class);
        }
        return bucket.get(handle);
    }


    /**
     * 根据类型 获取对应的处理器
     * @param expect 期望对象
     * @param actual 实际对象
     * @return 返回的处理器
     */
    public static Handle getHandle(Object expect, Object actual) {
        // TODO 根据类型解析出不同的处理器
        return null;
    }

    /**
     * 根据类型直接进行处理
     * @param expect 期望对象
     * @param actual 实际对象
     * @return 处理结果
     */
    public static DiffProcessResultStack handle(Object expect, Object actual) {
        Handle handle = getHandle(expect, actual);
        assert handle != null;
        return handle.handle(expect, actual);
    }


}
