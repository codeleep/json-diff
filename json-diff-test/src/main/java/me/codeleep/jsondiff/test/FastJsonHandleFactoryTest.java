package me.codeleep.jsondiff.test;

import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.DefaultJsonDifference;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.TravelPath;
import me.codeleep.jsondiff.common.model.neat.JsonDiff;
import me.codeleep.jsondiff.common.model.neat.JsonDiffArray;
import me.codeleep.jsondiff.common.model.neat.JsonDiffObject;
import me.codeleep.jsondiff.common.model.neat.JsonNeat;
import me.codeleep.jsondiff.common.utils.ImplType;
import me.codeleep.jsondiff.core.config.JsonComparedOption;
import me.codeleep.jsondiff.core.config.JsonDiffOption;
import me.codeleep.jsondiff.core.handle.AbstractHandleFactory;
import me.codeleep.jsondiff.core.handle.array.ComplexArrayJsonNeat;
import me.codeleep.jsondiff.core.handle.object.ComplexObjectJsonNeat;
import me.codeleep.jsondiff.core.handle.other.ComplexOtherJsonNeat;
import me.codeleep.jsondiff.core.handle.primitive.ComplexPrimitiveJsonNeat;
import me.codeleep.jsondiff.core.utils.ClassUtil;
import me.codeleep.jsondiff.test.dataFactory.HandleFactoryDataFactory;
import me.codeleep.jsondiff.test.model.MetaData;
import me.codeleep.jsondiff.test.utils.FormatContent;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author: chenfeng
 * @createTime: 2024/4/21
 * @description:
 */
public class FastJsonHandleFactoryTest {
    private static final DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
    @Test(dataProvider = "right", dataProviderClass = HandleFactoryDataFactory.class)
    public void RightTest(MetaData metaData) {
        JsonDiffOption.setDefaultJsonFramework(ImplType.FASTJSON);
        JsonComparedOption jsonComparedOption = new JsonComparedOption();
        jsonComparedOption.setJsonNeatFactory(new AbstractHandleFactory() {
            class ComplexObjectJsonNeatTest extends ComplexObjectJsonNeat {
                private ComplexObjectJsonNeatTest(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
                    super(travelPath, DisposeExpect(expect), actual);
                }
                @Override
                protected JsonCompareResult diff1() {
                    return super.diff1();
                }
            }
            class ComplexArrayJsonNeatTest extends ComplexArrayJsonNeat {
                private ComplexArrayJsonNeatTest(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
                    super(travelPath, DisposeExpect(expect), actual);
                }
                @Override
                protected JsonCompareResult diff1() {
                    return super.diff1();
                }
            }
            private JsonDiff DisposeExpect(JsonDiff expect) {
                return expect;
            }

            @Override
            public JsonNeat<? extends JsonDiff> generate(TravelPath travelPath, JsonDiff expect, JsonDiff actual) {
                if (!ClassUtil.isSameClass(expect, actual)) {
                    return null;
                }
                if (expect instanceof JsonDiffObject && actual instanceof JsonDiffObject) {
                    return new ComplexObjectJsonNeatTest(travelPath, expect, actual);
                }
                if (expect instanceof JsonDiffArray && actual instanceof JsonDiffArray) {
                    return new ComplexArrayJsonNeatTest(travelPath, expect, actual);
                }
                if (expect.isLeaf() && actual.isLeaf()) {
                    return new ComplexPrimitiveJsonNeat(travelPath, expect, actual);
                }
                return new ComplexOtherJsonNeat(travelPath, expect, actual);
            }
        });
        JsonCompareResult jsonCompareResult = defaultJsonDifference.option(jsonComparedOption)
                .detectDiff(JSON.toJSONString(metaData.getExpect()), JSON.toJSONString(metaData.getActual()));
        if (metaData.getRet() != null) {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent(metaData.getRet().toString()));
        } else {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent("{\"match\":true}"));
        }
    }
}
