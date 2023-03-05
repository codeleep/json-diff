package me.codeleep.jsondiff.common.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: codeleep
 * @createTime: 2023/02/20 17:39
 * @description: 路径工具类
 */
public class PathUtil {

    private static final String OBJECT_SING = ".";

    private static final String ARRAY_SING_LEFT = "[";
    private static final String ARRAY_SING_RIGHT = "]";


    public static String getIndexPath(String index) {
        return ARRAY_SING_LEFT + index + ARRAY_SING_RIGHT;
    }


    public static String getObjectPath(String parentPath) {
        return parentPath + OBJECT_SING;
    }


    /**
     * 将下标填入path
     * @param path 路径地址
     * @param index 下标
     * @return
     */
    public static String insertPathIndex(String path, int index) {
        if (path == null || path.length() == 0 || index < 0) {
            return path;
        }
        // 正则表达式匹配"]["
        Pattern pattern = Pattern.compile("\\]\\[");
        Matcher matcher = pattern.matcher(new StringBuilder(path).reverse());
        StringBuilder sb = new StringBuilder(path);
        sb.reverse();
        // 循环匹配"[]"，并记录匹配到的最右边的位置
        if (matcher.find()) {
            // 记录第一个"[]"出现的位置
            int i = matcher.start();
            sb.insert(i + 1, index); // 在"[]"中间插入数字
        }
        return sb.reverse().toString();
    }


}
