package me.codeleep.jsondiff.core.handle.custom;

import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.core.handle.array.ComplexArrayJsonNeat;

import java.util.Collections;

/**
 * @author: codeleep
 * @createTime: 2023/04/25 22:22
 * @description:
 */
public class AlignArrayJsonDiff extends ComplexArrayJsonNeat {

    protected AlignArrayJsonDiff(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
        super(travelPath, expect, actual);
    }

    @Override
    protected JsonCompareResult diff1() {
        // 长度不一致
        int expectSize = expect.size();
        int actualSize = actual.size();
        if (expectSize == actualSize) {
            return super.diff1();
        }
        // 让期望的数组长度和实际的数组长度一致。谁短补齐谁
        if (expectSize > actualSize) {
            actual.addAll(Collections.nCopies(expectSize - actualSize, null));
        } else {
            expect.addAll(Collections.nCopies(actualSize - expectSize, null));
        }
        return super.diff1();
    }

}
