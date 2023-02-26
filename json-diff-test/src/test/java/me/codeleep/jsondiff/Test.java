package me.codeleep.jsondiff;


import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.JsonComparedOption;

import java.util.Stack;
import java.util.function.Function;

/**
 * @author: codeleep
 * @createTime: 2022/11/22 16:57
 * @description:
 */
public class Test {

    public static void main(String[] args) {
        String array1 = "[{\"id\":1,\"name\":\"lin\",\"value\":1},{\"id\":2,\"name\":\"lin\"}]";
        String array2 = "[{\"id\":1,\"name\":\"lin\",\"value\":3},{\"id\":1,\"name\":\"lin\",\"value\":1}]";

        Function<String, Stack<String>> keyFunction = new Function<String, Stack<String>>() {
            @Override
            public Stack<String> apply(String s) {
                Stack<String> stack = new Stack<String>();
                stack.add("id");
                return stack;
            }
        };
        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true);
        // 初始化工具
        JsonCompareResult jsonCompareResult = new DefaultJsonDifference()
                .option(jsonComparedOption)
                // 对比
                .detectDiff(JSON.parseArray(array1), JSON.parseArray(array2));
        System.out.println(JSON.toJSONString(jsonCompareResult));
    }
}
