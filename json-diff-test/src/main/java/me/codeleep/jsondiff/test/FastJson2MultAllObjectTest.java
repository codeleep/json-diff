package me.codeleep.jsondiff.test;

import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.DefaultJsonDifference;
import me.codeleep.jsondiff.common.utils.ImplType;
import me.codeleep.jsondiff.core.config.JsonDiffOption;
import me.codeleep.jsondiff.test.dataFactory.ObjectDataFactory;
import me.codeleep.jsondiff.test.model.MetaData;
import me.codeleep.jsondiff.test.utils.FormatContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author: chenfeng
 * @createTime: 2023/3/2 21:55
 * @description: 字典类型的测试类
 */
public class FastJson2MultAllObjectTest {
    private static final Logger logger = LoggerFactory.getLogger(FastJson2MultAllObjectTest.class);
    private static final DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
    @Test(dataProvider = "right", dataProviderClass = ObjectDataFactory.class)
    public void noOptionRightTest(MetaData metaData) {
        JsonDiffOption.setDefaultJsonFramework(ImplType.FASTJSON2);
        logger.info(metaData.getCaseName());
        logger.debug(metaData.getExpect().toString() + "\n" + metaData.getActual().toString());
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .detectDiff(JSON.toJSONString(metaData.getExpect()), JSON.toJSONString(metaData.getActual()));
        if (metaData.getRet() != null) {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent(metaData.getRet().toString()));
        } else {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent("{\"match\":true}"));
        }
    }

    @Test(dataProvider = "err", dataProviderClass = ObjectDataFactory.class)
    public void noOptionErrTest(MetaData metaData) {
        JsonDiffOption.setDefaultJsonFramework(ImplType.FASTJSON2);
        logger.info(metaData.getCaseName());
        logger.debug(metaData.getExpect().toString() + "\n" + metaData.getActual().toString());
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .detectDiff(JSON.toJSONString(metaData.getExpect()), JSON.toJSONString(metaData.getActual()));
        Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent(metaData.getRet().toString()));
    }

    @Test(dataProvider = "optionRight", dataProviderClass = ObjectDataFactory.class)
    public void optionRight(MetaData metaData) {
        JsonDiffOption.setDefaultJsonFramework(ImplType.FASTJSON2);
        logger.info(metaData.getCaseName());
        logger.debug("\n" + metaData.getExpect().toString() + "\n" + metaData.getActual().toString() + "\n");
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(metaData.getOption())
                .detectDiff(JSON.toJSONString(metaData.getExpect()), JSON.toJSONString(metaData.getActual()));
        if (metaData.getRet() != null) {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent(metaData.getRet().toString()));
        } else {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent("{\"match\":true}"));
        }
    }

    @Test(dataProvider = "optionErr", dataProviderClass = ObjectDataFactory.class)
    public void optionErr(MetaData metaData) {
        JsonDiffOption.setDefaultJsonFramework(ImplType.FASTJSON2);
        logger.info(metaData.getCaseName());
        logger.debug("\n" + metaData.getExpect().toString() + "\n" + metaData.getActual().toString() + "\n");
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(metaData.getOption())
                .detectDiff(JSON.toJSONString(metaData.getExpect()), JSON.toJSONString(metaData.getActual()));
        Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent(metaData.getRet().toString()));
    }
}
