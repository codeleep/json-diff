package me.codeleep.jsondiff.test.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import me.codeleep.jsondiff.common.model.JsonCompareResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: chenfeng
 * @createTime: 2024/4/14 10:55
 * @description: 格式化内容
 */
public class FormatContent {
    private static final String REGEX = "[a-zA-Z]+(\\.[a-zA-Z0-9]+)+";
    private static final Pattern pattern = Pattern.compile(REGEX);

    /**
     * 将返回的JsonCompareResult类转化为格式化的JSON字符串 并将其中的关于类型的部分去除 例如java.math.BigDecimal
     * @param jsonCompareContent JsonCompareResult 执行对比后返回的内容
     * @return String 格式化后的JSON字符串
     */
    public static String formatComparisonContent(JsonCompareResult jsonCompareContent) {
     return formatComparisonContent(JSON.toJSONString(jsonCompareContent));
    }
    /**
     * 将String字符串转化为格式化的JSON字符串 并将其中的关于类型的部分去除 例如java.math.BigDecimal
     * @param content String 需要格式化的字符串内容
     * @return String 格式化后的JSON字符串
     */
    public static String formatComparisonContent(String content) {
        Matcher matcher = pattern.matcher(content);
        JSONObject jsonObject = JSON.parseObject(matcher.replaceAll(""));
        return JSON.toJSONString(jsonObject, JSONWriter.Feature.PrettyFormat);
    }
}
