package me.codeleep.jsondiff.core.handle.other;

import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffOther;
import me.codeleep.jsondiff.core.utils.ClassUtil;

import static me.codeleep.jsondiff.common.model.Constant.DATA_INCONSISTENT;
import static me.codeleep.jsondiff.common.model.Constant.DATA_TYPE_INCONSISTENT;

/**
 * @author: codeleep
 * @createTime: 2023/02/22 22:53
 * @description:
 */
public class ComplexOtherJsonNeat extends AbstractOtherJsonNeat<JsonDiffOther> {

    public ComplexOtherJsonNeat(TravelPath travelPath, JsonDiff actual, JsonDiff expect) {
        super(travelPath, actual, expect);
    }

    @Override
    protected JsonCompareResult diff1() {
        if (expect == null && actual == null) {
            return result;
        }
        if (!ClassUtil.isSameClass(expect, actual)) {
            Defects defects = new Defects()
                    .setActual(actual)
                    .setExpect(expect)
                    .setTravelPath(travelPath)
                    .setIllustrateTemplate(DATA_TYPE_INCONSISTENT, ClassUtil.getClassName(expect), ClassUtil.getClassName(actual));
            result.addDefects(defects);
            return result;
        }
        if (expect.isEquals(actual)) {
            return result;
        }
        Defects defects = new Defects()
                .setActual(actual)
                .setExpect(expect)
                .setTravelPath(travelPath)
                .setIllustrateTemplate(DATA_INCONSISTENT, String.valueOf(expect), String.valueOf(actual));
        result.addDefects(defects);
        return result;
    }
}
