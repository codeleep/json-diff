package me.codeleep.jsondiff.test;

import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.DefaultJsonDifference;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.utils.ImplType;
import me.codeleep.jsondiff.core.config.JsonComparedOption;
import me.codeleep.jsondiff.core.config.JsonDiffOption;
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
        String expect = "{\"name\": null}";
        String actual = "{\"name\": null}";

        HashSet<String> ignorePath = new HashSet<>();

        JsonDiffOption.setDefaultJsonFramework(ImplType.FASTJSON2);

        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true).setIgnorePath(ignorePath);
        JsonCompareResult jsonCompareResult = new DefaultJsonDifference()
                .option(jsonComparedOption)
                .detectDiff(expect, actual);
        System.out.printf(JSON.toJSONString(jsonCompareResult));
    }
}
