package me.codeleep.jsondiff.core.handle;

import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonNeat;
import me.codeleep.jsondiff.core.utils.RunTimeDataFactory;

import static me.codeleep.jsondiff.common.model.Constant.DATA_TYPE_INCONSISTENT;

/**
 * @author: codeleep
 * @createTime: 2024/04/10 下午11:09
 * @description:
 */
public abstract class AbstractJsonNeat<T extends JsonDiff> implements JsonNeat<T> {

    protected final TravelPath travelPath;

    protected final JsonCompareResult result = new JsonCompareResult();

    protected boolean diffed = false;

    protected AbstractJsonNeat(TravelPath travelPath) {
        this.travelPath = travelPath;
    }

    @Override
    public JsonCompareResult diff() {
        if (diffed) {
            throw new JsonDiffException("diff has already been run");
        }
        diffed = true;
        // 如果都为null。则返回
        if (getActualJsonDiff() == null && getExpectJsonDiff() == null) {
            return result;
        }
        // 如果符合忽略路径。则返回null
        if (RunTimeDataFactory.getOptionInstance().getIgnorePath().contains(travelPath.getAbstractTravelPath())) {
            return result;
        }
        // 如果一个不为null
        if (getActualJsonDiff() == null || getExpectJsonDiff() == null) {
            Defects defects = new Defects()
                    .setActual(getActualJsonDiff())
                    .setExpect(getExpectJsonDiff())
                    .setTravelPath(travelPath)
                    .setIllustrateTemplate(DATA_TYPE_INCONSISTENT, getExpectJsonDiff() == null? null: String.valueOf(getExpectJsonDiff().format()), getActualJsonDiff() == null? null: String.valueOf(getActualJsonDiff().format()));
            result.addDefects(defects);
            return result;
        }
        return diff0();
    }

    @Override
    public int leaf() {
        int count = 0;
        if (getActualJsonDiff() == null || getActualJsonDiff().isLeaf()) {
            count++;
        }
        if (getExpectJsonDiff() == null || getExpectJsonDiff().isLeaf()) {
            count++;
        }
        return count;
    }

    protected abstract JsonCompareResult diff0();

    @Override
    public JsonCompareResult getResult() {
        return this.result;
    }

    @Override
    public TravelPath getTravelPath() {
        return this.travelPath;
    }
}
