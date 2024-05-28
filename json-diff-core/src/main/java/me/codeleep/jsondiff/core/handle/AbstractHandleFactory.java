package me.codeleep.jsondiff.core.handle;

import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.*;
import me.codeleep.jsondiff.core.handle.array.AbstractArrayJsonNeat;
import me.codeleep.jsondiff.core.handle.array.ComplexArrayJsonNeat;
import me.codeleep.jsondiff.core.handle.object.AbstractObjectJsonNeat;
import me.codeleep.jsondiff.core.handle.object.ComplexObjectJsonNeat;
import me.codeleep.jsondiff.core.handle.other.AbstractOtherJsonNeat;
import me.codeleep.jsondiff.core.handle.other.ComplexOtherJsonNeat;
import me.codeleep.jsondiff.core.handle.primitive.AbstractPrimitiveJsonNeat;
import me.codeleep.jsondiff.core.handle.primitive.ComplexPrimitiveJsonNeat;
import me.codeleep.jsondiff.core.utils.ClassUtil;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 上午10:39
 * @description:
 */
public class AbstractHandleFactory implements HandleFactory{
    @Override
    public JsonNeat<? extends JsonDiff> generate(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
        if (!ClassUtil.isSameClass(expect, actual)) {
            return null;
        }
        // TODO 返回系统默认处理器
        if (expect instanceof JsonDiffObject && actual instanceof JsonDiffObject) {
            return generateObjectJsonNeat(travelPath, expect, actual);
        }
        if (expect instanceof JsonDiffArray && actual instanceof JsonDiffArray) {
            return generateArrayJsonNeat(travelPath, expect, actual);
        }
        if (expect.isLeaf() && actual.isLeaf()) {
            return generatePrimitiveJsonNeat(travelPath, expect, actual);
        }
        return generateOtherJsonNeat(travelPath, expect, actual);
    }

    public AbstractObjectJsonNeat<JsonDiffObject> generateObjectJsonNeat(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
        return new ComplexObjectJsonNeat(travelPath, expect, actual);
    }

    public AbstractArrayJsonNeat<JsonDiffArray> generateArrayJsonNeat(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
        return new ComplexArrayJsonNeat(travelPath, expect, actual);
    }

    public AbstractPrimitiveJsonNeat<JsonDiffPrimitive> generatePrimitiveJsonNeat(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
        return new ComplexPrimitiveJsonNeat(travelPath, expect, actual);
    }

    public AbstractOtherJsonNeat<JsonDiffOther> generateOtherJsonNeat(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
        return new ComplexOtherJsonNeat(travelPath, expect, actual);
    }

}
