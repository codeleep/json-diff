package me.codeleep.jsondiff.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.exception.JsonDiffException;
import me.codeleep.jsondiff.handle.array.IntricacyArrayHandle;
import me.codeleep.jsondiff.handle.array.MultidimensionalArrayHandle;
import me.codeleep.jsondiff.handle.array.ObjectArrayHandle;
import me.codeleep.jsondiff.handle.array.SimpleArrayHandle;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static me.codeleep.jsondiff.handle.JsonDiffConstants.SIGN;

public class JsonDiffUtil {

    /**
     * 判断当前对象是否为json数据格式中的基本类型
     * @param obj
     * @return
     */
    public static boolean isPrimitiveType(Object obj){

        if(obj == null){
            return true;
        }

        if(obj instanceof JSONArray || obj instanceof JSONObject){
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

    /**
     * 从两种角度划分, 1. 元素是都是一种类型 2. 元素是否是基本类型
     * 类型只分为基本类型和复杂类型。复杂类型其实也只有JSONObject
     *  一共可以划分出四种可能
     * @return
     */
    public static Class<?> getArrayHandleClass(JSONArray expect, JSONArray actual) {

        Set<Class<?>> typeSet = new HashSet<>();
        for (Object item: expect) {
            typeSet.add(parseItemClass(item));
        }
        for (Object item: actual) {
            typeSet.add(parseItemClass(item));
        }

        if (typeSet.size() > 1) {
            return IntricacyArrayHandle.class;
        }

        if (typeSet.size() == 0) {
            throw new JsonDiffException("没有识别到正确的元素类型");
        }
        return typeSet.stream().findAny().get();
    }

    public static Class<?> getObjectHandleClass(JSONObject expect, JSONObject actual) {
        return HandleBucket.getObjectHandle();
    }

    /**
     * 数组的元素 1. 基本类型  2. 对象  3. 数组
     * 暂时不支持元素是多种类型的数据
     * @param item
     * @return
     */
    public static Class<?> parseItemClass(Object item) {
        if(isPrimitiveType(item)) {
            return SimpleArrayHandle.class;
        }
        if (item instanceof JSONArray) {
            return MultidimensionalArrayHandle.class;
        }
        if (item instanceof JSONObject) {
            return ObjectArrayHandle.class;
        }
        return SimpleArrayHandle.class;
    }

    /**
     * 处理json path
     * @return
     */
    public static String getCurrentPath(Stack<String> strings) {
        String[] paths = strings.toArray(new String[0]);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            stringBuilder.append(paths[i]);
            if (i >= paths.length -1) {
                continue;
            }
            if (!(paths[i + 1].startsWith("[") && paths[i + 1].endsWith("]"))) {
                stringBuilder.append(SIGN);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 转化当前的临时path
     * @param root
     * @param tempPath
     * @return
     */
    public static String convertPath(String root, Stack<String> tempPath) {
        if (root.trim().equals("")) {
            return JsonDiffUtil.getCurrentPath(tempPath);
        }
        if (tempPath == null || tempPath.size() == 0) {
            return root;
        }
        return String.format("%s.%s", root, JsonDiffUtil.getCurrentPath(tempPath));
    }


}
