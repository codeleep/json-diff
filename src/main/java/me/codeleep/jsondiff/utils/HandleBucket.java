package me.codeleep.jsondiff.utils;

import me.codeleep.jsondiff.handle.object.SimpleObjectHandle;

/**
 * @author: codeleep
 * @createTime: 2022/07/31 09:15
 * @description:
 */
public class HandleBucket {

    public static Class<?> getObjectHandle() {
        return SimpleObjectHandle.class;
    }

}
