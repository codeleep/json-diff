package me.codeleep.jsondiff;

import me.codeleep.jsondiff.spi.model.array.DiffJsonArray;
import me.codeleep.jsondiff.spi.model.object.DiffJsonObject;
import me.codeleep.jsondiff.model.JsonCompareResult;

public interface JsonDifference {


    JsonCompareResult detectDiff(DiffJsonObject expect, DiffJsonObject actual) throws IllegalAccessException;

    JsonCompareResult detectDiff(DiffJsonArray expect, DiffJsonArray actual) throws IllegalAccessException;






}
