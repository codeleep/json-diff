package me.codeleep.jsondiff.core.handle;

import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonNeat;

/**
 * @author: codeleep
 * @createTime: 2024/04/11 上午10:35
 * @description: Generate JsonNeat
 */
public interface HandleFactory {

    JsonNeat<? extends JsonDiff> generate(TravelPath travelPath, JsonDiff expect, JsonDiff actual);

}
