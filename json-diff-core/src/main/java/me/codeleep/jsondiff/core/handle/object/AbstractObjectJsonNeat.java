package me.codeleep.jsondiff.core.handle.object;


import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;
import me.codeleep.jsondiff.core.handle.AbstractJsonNeat;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 19:29
 * @description: 抽象比较器
 */
public abstract class AbstractObjectJsonNeat<T extends JsonDiffObject> extends AbstractJsonNeat<T> {

    protected final JsonDiffObject actual;

    protected final JsonDiffObject expect;

    protected AbstractObjectJsonNeat(TravelPath travelPath, JsonDiff actual, JsonDiff expect) {
        super(travelPath);
        if (!(actual instanceof JsonDiffObject) || !(expect instanceof JsonDiffObject)) {
            throw new IllegalArgumentException("Parameter type error, actual and expect must be JsonDiffObject");
        }
        this.actual = (JsonDiffObject) actual;
        this.expect = (JsonDiffObject) expect;
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
