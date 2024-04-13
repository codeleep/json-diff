package me.codeleep.jsondiff.test;

import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.DefaultJsonDifference;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.core.config.JsonComparedOption;
import org.testng.annotations.Test;

import java.util.HashSet;

/**
 * @author: codeleep
 * @createTime: 2024/04/12 上午10:22
 * @description:
 */
public class SimpleTest {

    @Test
    public void test1() {
        String expect = "{\"name\":\"baron\",\"age\":12}";
        String actual = "{\"name\":\"baron\"}";

        HashSet<String> ignorePath = new HashSet<>();
        ignorePath.add("$.age");

        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true).setIgnorePath(ignorePath);
        JsonCompareResult jsonCompareResult = new DefaultJsonDifference()
                .option(jsonComparedOption)
                .detectDiff(expect, actual);
        System.out.printf(JSON.toJSONString(jsonCompareResult));
    }
}
