package me.codeleep.jsondiff.main.utils;

import me.codeleep.jsondiff.spi.model.object.AbstractDiffJsonObject;

import java.util.HashSet;
import java.util.Set;

import static me.codeleep.jsondiff.common.model.Constant.JOIN_SPILT;

/**
 * @author: codeleep
 * @createTime: 2022/08/17 23:36
 * @description: 字符串谷类
 */
public class StringUtil {


    /**
     * 匹配两个路径是否符合要求
     * @param currentPath 当前的path
     * @param ignorePath 忽略的path
     * @return 是否匹配
     */
    public static boolean pathPattern(String currentPath, String ignorePath) {
        char[] currentPathChar = currentPath.toCharArray();
        char[] ignorePathChar = ignorePath.toCharArray();
        int i, j = 0;
        try {
            for (i = 0; i < currentPathChar.length; i++) {
                char cuChar = currentPathChar[i];
                char igChar = ignorePathChar[j];
                if (cuChar == '[' && igChar == '[') {
                    while (currentPathChar[i] != ']') {
                        i ++;
                    }
                    j ++;
                }
                if (ignorePathChar[j] != currentPathChar[i]) {
                    return false;
                }
                j ++;
            }
        }catch (Exception e) {
            return false;
        }

        return i == currentPathChar.length && j == ignorePathChar.length;
    }


    /**
     * 将value拼接串一个字符串
     * @param object json 对象
     * @param keys 对象的key
     * @return 拼接后的字符串
     */
    public static String joining(AbstractDiffJsonObject object, Set<String> keys) {
        StringBuilder builder = new StringBuilder();
        if (keys == null) {
            keys = object.keySet();
        }
        for (String key: keys) {
            builder.append(String.valueOf(object.get(key)).substring(0, 20));
            builder.append(JOIN_SPILT);
        }
        return builder.toString();
    }


}
