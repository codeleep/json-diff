package me.codeleep.jsondiff.core;

import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.core.config.JsonComparedOption;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonNeat;
import me.codeleep.jsondiff.core.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.core.utils.JsonDiffBuilder;

import static me.codeleep.jsondiff.common.model.Constant.PATH_ROOT;

/**
 * @author: codeleep
 * @createTime: 2023/02/25 23:43
 * @description: 面向外部的类
 */
public class DefaultJsonDifference {

    public JsonCompareResult detectDiff(String expect, String actual) {
        JsonDiff expectJson = JsonDiffBuilder.buildObject(expect);
        JsonDiff actualJson = JsonDiffBuilder.buildObject(actual);

        TravelPath travelPath = new TravelPath(PATH_ROOT);
        JsonNeat<? extends JsonDiff> jsonNeat = RunTimeDataFactory.getOptionInstance().getJsonNeatFactory().generate(actualJson, expectJson, travelPath);
        if (jsonNeat == null) {
            throw new JsonDiffException("无法找到适配比较器");
        }
        JsonCompareResult result = jsonNeat.diff();
        // 清除设置
        RunTimeDataFactory.clearOptionInstance();
        return  result;
    }

    public DefaultJsonDifference option(JsonComparedOption option) {
        RunTimeDataFactory.setOptionInstance(option);
        return this;
    }

}
