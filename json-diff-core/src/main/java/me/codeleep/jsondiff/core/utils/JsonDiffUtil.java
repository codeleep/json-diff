package me.codeleep.jsondiff.core.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.ComparatorEnum;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonNeat;
import me.codeleep.jsondiff.core.config.JsonDiffOption;
import me.codeleep.jsondiff.core.handle.array.AbstractArrayJsonNeat;
import me.codeleep.jsondiff.core.handle.object.AbstractObjectJsonNeat;
import me.codeleep.jsondiff.core.handle.primitive.AbstractPrimitiveJsonNeat;

public class JsonDiffUtil {


    /**
     * 判断两个对象应该采用什么比较器
     * @param expect 期望对象
     * @param actual 实际对象
     * @return 比较器实例
     */
    public static JsonNeat getJsonNeat(Object expect, Object actual, TravelPath travelPath) {
        if (!ClassUtil.isSameClass(expect, actual)) {
            return null;
        }
        boolean defaultNeat = RunTimeDataFactory.getOptionInstance().isMandatoryDefaultNeat();
        Class<? extends JsonNeat> customComparator = JsonDiffOption.getJsonNeatFactory().getCustomComparator(travelPath.getAbstractTravelPath());
        boolean custom = customComparator != null;
        // 返回系统默认处理器
        if (expect instanceof JSONObject && actual instanceof JSONObject) {
            return custom ? selectionCustomJsonNeat(customComparator, ComparatorEnum.OBJECT) : JsonDiffOption.getJsonNeatFactory().getObjectJsonNeatInstance(defaultNeat);
        }
        if (expect instanceof JSONArray && actual instanceof JSONArray) {
            return custom ? selectionCustomJsonNeat(customComparator, ComparatorEnum.ARRAY) : JsonDiffOption.getJsonNeatFactory().getArrayJsonNeatInstance(defaultNeat);
        }
        if (ClassUtil.isPrimitiveType(expect) && ClassUtil.isPrimitiveType(actual)) {
            return custom ? selectionCustomJsonNeat(customComparator, ComparatorEnum.PRIMITIVE) : JsonDiffOption.getJsonNeatFactory().getPrimitiveJsonNeatInstance(defaultNeat);
        }
        return null;
    }

    /**
     * 判断传入的是否是一个合法处理器实现类
     * @param customComparatorClass 自定义实现类
     * @param comparator 比较器类型
     * @return 用户比较器实例
     */
    private static JsonNeat selectionCustomJsonNeat(Class<? extends JsonNeat> customComparatorClass, ComparatorEnum comparator) {
        JsonNeat jsonNeat = null;
        try {
            jsonNeat = customComparatorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JsonDiffException(String.format("无法实例化自定义比较器: %s", customComparatorClass), e);
        }
        // 判断是否实现对应的类型
        switch(comparator) {
            case OBJECT:
                if (jsonNeat instanceof AbstractObjectJsonNeat) {
                    return jsonNeat;
                }
                throw new JsonDiffException(String.format("自定义比较器未继承%s", AbstractObjectJsonNeat.class.getName()));
            case ARRAY:
                if (jsonNeat instanceof AbstractArrayJsonNeat) {
                    return jsonNeat;
                }
                throw new JsonDiffException(String.format("自定义比较器未继承%s", AbstractArrayJsonNeat.class.getName()));
            case PRIMITIVE:
                if (jsonNeat instanceof AbstractPrimitiveJsonNeat) {
                    return jsonNeat;
                }
                throw new JsonDiffException(String.format("自定义比较器未继承%s", AbstractPrimitiveJsonNeat.class.getName()));
            default:
                throw new JsonDiffException("类型错误");
        }
    }


}
