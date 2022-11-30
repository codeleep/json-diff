package me.codeleep.jsondiff.main.utils;

import me.codeleep.jsondiff.handle.HandleExampleFactory;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.handle.array.AbstractArrayHandle;
import me.codeleep.jsondiff.handle.object.AbstractObjectHandle;
import me.codeleep.jsondiff.model.Defects;
import me.codeleep.jsondiff.spi.model.array.DiffJsonArray;
import me.codeleep.jsondiff.spi.model.object.AbstractDiffJsonObject;
import me.codeleep.jsondiff.spi.model.object.DiffJsonObject;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 * @author: codeleep
 * @createTime: 2022/08/15 22:22
 * @description: 比较的一些公共方法
 */
public class ComparedUtil {


    /**
     * 比较两个对象是否值得被比较
     * @param expect
     * @param actual
     * @param keys
     * @return
     */
    public static boolean isItWorthComparing(AbstractDiffJsonObject expect, AbstractDiffJsonObject actual, HashSet<String> keys) {
        boolean flag = true;
        for (String key: keys) {
            if (expect.get(key) != null && actual.get(key) != null) {
                if (NodeTypeUtil.isPrimitiveType(expect.get(key)) && NodeTypeUtil.isPrimitiveType(actual.get(key))) {
                    flag = !expect.get(key).equals(actual.get(key));
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }
}
