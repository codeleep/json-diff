package me.codeleep.jsondiff.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.handle.HandleExampleFactory;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.handle.array.AbstractArrayHandle;
import me.codeleep.jsondiff.handle.object.AbstractObjectHandle;
import me.codeleep.jsondiff.model.Defects;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: codeleep
 * @createTime: 2022/08/15 22:22
 * @description: 比较的一些公共方法
 */
public class ComparedUtil {


    /**
     * 元素不确定类型时
     * @param expect
     * @param actual
     * @throws IllegalAccessException
     */
    public static void notSureAboutComparison(Object expect, Object actual) throws IllegalAccessException {
        if (JsonDiffUtil.isPrimitiveType(expect)) {
            if (!expect.equals(actual)) {
                Defects defects = new Defects()
                        .setActual(actual)
                        .setExpect(expect)
                        .setIndexPath(JsonDiffUtil.getCurrentPath(RunTimeDataFactory.getCurrentPathInstance().getPath()))
                        .setIllustrate("properties are different");
                RunTimeDataFactory.getResultInstance().addDefects(defects);
            }
        }else if (expect instanceof JSONArray) {
            AbstractArrayHandle handle = (AbstractArrayHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getArrayHandleClass((JSONArray) expect, (JSONArray) actual));
            handle.handle((JSONArray) expect, (JSONArray) actual);
        }else if (expect instanceof JSONObject) {
            AbstractObjectHandle handle = (AbstractObjectHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getObjectHandleClass((JSONObject) expect, (JSONObject) actual));
            handle.handle((JSONObject) expect, (JSONObject) actual);
        }
    }


    /**
     * 比较两个对象是否值得被比较
     * @param expect
     * @param actual
     * @param keys
     * @return
     */
    public static boolean isItWorthComparing(JSONObject expect, JSONObject actual, Stack<String> keys) {
        boolean flag = true;
        for (String key: keys) {
            if (expect.get(key) != null && actual.get(key) != null) {
                if (JsonDiffUtil.isPrimitiveType(expect.get(key)) && JsonDiffUtil.isPrimitiveType(actual.get(key))) {
                    flag = !expect.get(key).equals(actual.get(key));
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否符合忽略的path
     * @return
     */
    public static boolean matchIgnoredPath(Stack<String> stack, List<String> ignorePath) {
        if (stack == null || ignorePath == null) {
            return false;
        }
        String currentPath = JsonDiffUtil.getCurrentPath(stack);
        System.out.println(currentPath);
        for (String path: ignorePath) {
            if (StringUtil.pathPattern(currentPath, path)) {
                return true;
            }
        }
        return false;
    }

}
