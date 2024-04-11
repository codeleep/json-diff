package me.codeleep.jsondiff.core.handle.array;


import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;
import me.codeleep.jsondiff.core.handle.AbstractJsonNeat;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:29
 * @description: 抽象比较器
 */
public abstract class AbstractArrayJsonNeat<T extends JsonDiffArray> extends AbstractJsonNeat<T> {


    protected final JsonDiffArray actual;

    protected final JsonDiffArray expect;

    protected AbstractArrayJsonNeat(TravelPath travelPath, JsonDiff actual, JsonDiff expect) {
        super(travelPath);
        if (!(actual instanceof JsonDiffArray) || !(expect instanceof JsonDiffArray)) {
            throw new IllegalArgumentException("Parameter type error, actual and expect must be JsonDiffArray");
        }
        this.actual = (JsonDiffArray) actual;
        this.expect = (JsonDiffArray) expect;
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
