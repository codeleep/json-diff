package me.codeleep.jsondiff.core.handle;

import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;
import me.codeleep.jsondiff.common.model.neat.JsonNeat;
import me.codeleep.jsondiff.core.handle.array.ComplexArrayJsonNeat;
import me.codeleep.jsondiff.core.handle.object.ComplexObjectJsonNeat;
import me.codeleep.jsondiff.core.handle.other.ComplexOtherJsonNeat;
import me.codeleep.jsondiff.core.handle.primitive.ComplexPrimitiveJsonNeat;
import me.codeleep.jsondiff.core.utils.ClassUtil;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 上午10:39
 * @description:
 */
public class AbstractHandleFactory implements HandleFactory{
    @Override
    public JsonNeat<? extends JsonDiff> generate(JsonDiff actual, JsonDiff expect, TravelPath travelPath) {
        if (!ClassUtil.isSameClass(expect, actual)) {
            return null;
        }
        // TODO 返回系统默认处理器
        if (expect instanceof JsonDiffObject && actual instanceof JsonDiffObject) {
            return new ComplexObjectJsonNeat(travelPath, actual, expect);
        }
        if (expect instanceof JsonDiffArray && actual instanceof JsonDiffArray) {
            return new ComplexArrayJsonNeat(travelPath, actual, expect);
        }
        if (expect.isLeaf() && actual.isLeaf()) {
            return new ComplexPrimitiveJsonNeat(travelPath, actual, expect);
        }
        return new ComplexOtherJsonNeat(travelPath, actual, expect);
    }
}
