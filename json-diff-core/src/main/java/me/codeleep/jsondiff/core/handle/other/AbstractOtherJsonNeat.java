package me.codeleep.jsondiff.core.handle.other;


import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;
import me.codeleep.jsondiff.common.model.neat.JsonDiffOther;
import me.codeleep.jsondiff.common.model.neat.JsonDiffPrimitive;
import me.codeleep.jsondiff.core.handle.AbstractJsonNeat;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:29
 * @description:
 */
public abstract class AbstractOtherJsonNeat<T extends JsonDiffOther> extends AbstractJsonNeat<T> {

    protected final JsonDiffOther actual;

    protected final JsonDiffOther expect;

    protected AbstractOtherJsonNeat(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
        super(travelPath);
        if (!(expect instanceof JsonDiffOther) || !(actual instanceof JsonDiffOther)) {
            throw new IllegalArgumentException("Parameter type error, actual and expect must be JsonDiffOther");
        }
        this.actual = (JsonDiffOther) actual;
        this.expect = (JsonDiffOther) expect;
    }

    @Override
    public T getExpectJsonDiff() {
        return (T)actual;
    }

    @Override
    public T getActualJsonDiff() {
        return (T)expect;
    }

    @Override
    protected JsonCompareResult diff0() {
        return diff1();
    }

    protected abstract JsonCompareResult diff1();
}
