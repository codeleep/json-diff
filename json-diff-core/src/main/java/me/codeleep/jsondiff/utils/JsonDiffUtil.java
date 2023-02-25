package me.codeleep.jsondiff.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.handle.array.ComplexArrayJsonNeat;
import me.codeleep.jsondiff.handle.object.ComplexObjectJsonNeat;
import me.codeleep.jsondiff.handle.primitive.PrimitiveTypeJsonNeat;
import me.codeleep.jsondiff.neat.JsonNeat;

public class JsonDiffUtil {


    /**
     * 判断两个对象应该采用什么比较器
     * @param expect 期望对象
     * @param actual 实际对象
     * @return 比较器实例
     */
    public static JsonNeat getJsonNeat(Object expect, Object actual) {
        if (!ClassUtil.isSameClass(expect, actual)) {
            return null;
        }
        if (expect instanceof JSONObject && actual instanceof JSONObject) {
            return new ComplexObjectJsonNeat();
        }
        if (expect instanceof JSONArray && actual instanceof JSONArray) {
            return new ComplexArrayJsonNeat();
        }
        if (ClassUtil.isPrimitiveType(expect) && ClassUtil.isPrimitiveType(actual)) {
            return new PrimitiveTypeJsonNeat();
        }
        return null;
    }


}
