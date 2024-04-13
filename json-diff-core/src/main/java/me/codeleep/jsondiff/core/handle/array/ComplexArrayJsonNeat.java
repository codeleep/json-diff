package me.codeleep.jsondiff.core.handle.array;

import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;
import me.codeleep.jsondiff.common.model.neat.JsonNeat;
import me.codeleep.jsondiff.core.utils.ClassUtil;
import me.codeleep.jsondiff.core.utils.RunTimeDataFactory;

import static me.codeleep.jsondiff.common.model.Constant.DATA_TYPE_INCONSISTENT;
import static me.codeleep.jsondiff.common.model.Constant.INCONSISTENT_ARRAY_LENGTH;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:24
 * @description:
 */
public class ComplexArrayJsonNeat extends AbstractArrayJsonNeat<JsonDiffArray> {

    public ComplexArrayJsonNeat(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
        super(travelPath, expect, actual);
    }

    protected JsonCompareResult ignoreOrder(JsonDiffArray expect, JsonDiffArray actual) {
        int expectIndex = 0;
        int actualIndex = 0;
        int len = expect.size();
        boolean[] expectFlag = new boolean[len];
        boolean[] actualFlag = new boolean[len];
        // 判断出所有能匹配的数据
        for (expectIndex = 0; expectIndex < len; expectIndex++) {
            if (expectFlag[expectIndex]) {
                continue;
            }
            for (actualIndex = 0; actualIndex < len; actualIndex++) {
                if (actualFlag[actualIndex]) {
                    continue;
                }
                TravelPath nextTravelPath = new TravelPath(this.travelPath, expectIndex, actualIndex);
                JsonNeat<? extends JsonDiff> jsonNeat = RunTimeDataFactory.getOptionInstance().getJsonNeatFactory().generate( expect.get(expectIndex), actual.get(actualIndex), nextTravelPath);
                if (jsonNeat == null) {
                    continue;
                }
                JsonCompareResult compareResult = jsonNeat.diff();
                if (compareResult != null && compareResult.isMatch()) {
                    expectFlag[expectIndex] = true;
                    actualFlag[actualIndex] = true;
                    break;
                }
            }
        }

        // 对所有未匹配数据进行报错
        for (expectIndex = 0; expectIndex < len; expectIndex++) {
            if (expectFlag[expectIndex]) {
                continue;
            }
            for (actualIndex = 0; actualIndex < len; actualIndex++) {
                if (actualFlag[actualIndex]) {
                    continue;
                }
                JsonDiff expectItem = expect.get(expectIndex);
                JsonDiff actualItem = actual.get(actualIndex);
                TravelPath nextTravelPath = new TravelPath(this.travelPath, expectIndex, actualIndex);
                // 判断类型, 根据类型去实例化JsonNeat。
                JsonNeat<? extends JsonDiff> jsonNeat = RunTimeDataFactory.getOptionInstance().getJsonNeatFactory().generate(expectItem, actualItem, nextTravelPath);
                // 类型不一致
                if (jsonNeat != null) {
                    JsonCompareResult diff = jsonNeat.diff();
                    // 将结果合并
                    if (!diff.isMatch()) {
                        result.mergeDefects(diff.getDefectsList());
                    }
                    continue;
                }
                Defects defects = new Defects()
                        .setActual(actualItem)
                        .setExpect(expectItem)
                        .setTravelPath(nextTravelPath)
                        .setIllustrateTemplate(DATA_TYPE_INCONSISTENT,  ClassUtil.getClassName(expectItem), ClassUtil.getClassName(actualItem));
                result.addDefects(defects);
            }
        }
        return result;
    }

    protected JsonCompareResult keepOrder(JsonDiffArray expect, JsonDiffArray actual) {
        int len = expect.size();
        for (int i = 0; i < len; i++) {
            JsonDiff expectItem = expect.get(i);
            JsonDiff actualItem = actual.get(i);
            TravelPath nextTravelPath = new TravelPath(this.travelPath, i, i);
            // 判断类型, 根据类型去实例化JsonNeat。
            JsonNeat<? extends JsonDiff> jsonNeat = RunTimeDataFactory.getOptionInstance().getJsonNeatFactory().generate(expectItem, actualItem, nextTravelPath);
            // 类型不一致
            if (jsonNeat == null) {
                Defects defects = new Defects()
                        .setExpect(expectItem)
                        .setActual(actualItem)
                        .setTravelPath(nextTravelPath)
                        .setIllustrateTemplate(DATA_TYPE_INCONSISTENT, ClassUtil.getClassName(expectItem), ClassUtil.getClassName(actualItem));
                result.addDefects(defects);
                continue;
            }
            JsonCompareResult diff = jsonNeat.diff();
            // 将结果合并
            if (!diff.isMatch()) {
                result.mergeDefects(diff.getDefectsList());
            }
        }
        return result;
    }

    @Override
    protected JsonCompareResult diff1() {
        // 长度不一致
        int expectSize = ((JsonDiffArray) expect).size();
        int actualSize = ((JsonDiffArray) actual).size();
        if (expectSize != actualSize) {
            Defects defects = new Defects()
                    .setActual(actualSize)
                    .setExpect(expectSize)
                    .setTravelPath(travelPath)
                    .setIllustrateTemplate(INCONSISTENT_ARRAY_LENGTH, String.valueOf(expectSize), String.valueOf(actualSize));
            result.addDefects(defects);
            return result;
        }
        boolean ignoreOrder = RunTimeDataFactory.getOptionInstance().isIgnoreOrder();
        if (ignoreOrder) {
            return ignoreOrder(expect, actual);
        }
        return keepOrder(expect, actual);
    }
}
