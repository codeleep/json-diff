package me.codeleep.jsondiff;

import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonNeat;
import me.codeleep.jsondiff.core.config.JsonComparedOption;
import me.codeleep.jsondiff.core.utils.JsonDiffBuilder;
import me.codeleep.jsondiff.core.utils.RunTimeDataFactory;

/**
 * @author: codeleep
 * @createTime: 2023/02/25 23:43
 * @description: 面向外部的类
 */
public class DefaultJsonDifference {

    public JsonCompareResult detectDiff(String expect, String actual) {
        JsonDiff expectJson = JsonDiffBuilder.buildObject(expect);
        JsonDiff actualJson = JsonDiffBuilder.buildObject(actual);
        return detectDiff(expectJson, actualJson);
    }

    public JsonCompareResult detectDiff(JsonDiff expect, JsonDiff actual) {
        JsonNeat<? extends JsonDiff> jsonNeat = RunTimeDataFactory.getOptionInstance().getJsonNeatFactory().generate(expect, actual, new TravelPath());
        if (jsonNeat == null) {
            throw new JsonDiffException("Unable to find JsonNeat");
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
