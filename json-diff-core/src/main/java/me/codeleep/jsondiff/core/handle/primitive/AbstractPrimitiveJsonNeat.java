package me.codeleep.jsondiff.core.handle.primitive;


import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffPrimitive;
import me.codeleep.jsondiff.core.handle.AbstractJsonNeat;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:29
 * @description: 抽象比较器
 */
public abstract class AbstractPrimitiveJsonNeat<T extends JsonDiffPrimitive> extends AbstractJsonNeat<T> {

    protected final JsonDiffPrimitive actual;

    protected final JsonDiffPrimitive expect;

    protected AbstractPrimitiveJsonNeat(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
        super(travelPath);
        if (!(expect instanceof JsonDiffPrimitive) || !(actual instanceof JsonDiffPrimitive)) {
            throw new IllegalArgumentException("Parameter type error, actual and expect must be JsonDiffPrimitive");
        }
        this.actual = (JsonDiffPrimitive) actual;
        this.expect = (JsonDiffPrimitive) expect;
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
