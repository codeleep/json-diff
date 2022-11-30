package me.codeleep.jsondiff;

import me.codeleep.jsondiff.spi.model.array.DiffJsonArray;
import me.codeleep.jsondiff.spi.model.object.DiffJsonObject;
import me.codeleep.jsondiff.handle.HandleExampleFactory;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.handle.array.AbstractArrayHandle;
import me.codeleep.jsondiff.handle.object.AbstractObjectHandle;
import me.codeleep.jsondiff.model.JsonCompareResult;
import me.codeleep.jsondiff.model.JsonComparedOption;
import me.codeleep.jsondiff.utils.JsonDiffUtil;

public class DefaultJsonDifference implements JsonDifference{

    @Override
    public JsonCompareResult detectDiff(DiffJsonObject expect, DiffJsonObject actual) {
        AbstractObjectHandle handle = (AbstractObjectHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getObjectHandleClass(expect, actual));
        handle.handle(expect, actual);
        return RunTimeDataFactory.remove();
    }

    @Override
    public JsonCompareResult detectDiff(DiffJsonArray expect, DiffJsonArray actual) {
        AbstractArrayHandle handle = (AbstractArrayHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getArrayHandleClass(expect, actual));
        handle.handle(expect, actual);
        return RunTimeDataFactory.remove();
    }

    public DefaultJsonDifference option(JsonComparedOption option) {
        RunTimeDataFactory.setOptionInstance(option);
        return this;
    }


}
