package me.codeleep.jsondiff.model;

import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.spi.array.DiffJsonArray;
import me.codeleep.jsondiff.spi.object.DiffJsonObject;

import java.util.List;

/**
 * @author: codeleep
 * @createTime: 2022/09/29 23:56
 * @description:
 */
public class DIFFJSON implements JSON {


    public static String toJSONString(DiffJsonObject object) {
        return JSON.toJSONString(object);
    }

    public static String toJSONString(DiffJsonArray array) {
        return null;
    }


    public static DiffJsonObject parse(String array) {
        return null;
    }

    public static String toJSONString(Object o) {
        return null;
    }

    public static <T> List<T> parseArray(String text, Class<T> type) {
        return null;
    }

}
