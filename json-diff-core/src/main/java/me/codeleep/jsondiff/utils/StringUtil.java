package me.codeleep.jsondiff.utils;

/**
 * @author: codeleep
 * @createTime: 2022/08/17 23:36
 * @description: 字符串谷类
 */
public class StringUtil {


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


}
