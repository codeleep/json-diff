package me.codeleep.jsondiff.test;

import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.DefaultJsonDifference;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.utils.ImplType;
import me.codeleep.jsondiff.core.config.JsonDiffOption;
import me.codeleep.jsondiff.test.dataFactory.ArrayDataFactory;
import me.codeleep.jsondiff.test.model.MetaData;
import me.codeleep.jsondiff.test.utils.FormatContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author: chenfeng
 * @createTime: 2024/4/21
 * @description:
 */
public class FastJsonMultAllArrayTest {
        private static final Logger logger = LoggerFactory.getLogger(FastJson2MultAllArrayTest.class);
        private final DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        @Test(dataProvider = "right", dataProviderClass = ArrayDataFactory.class)
        public void noOptionRightTest(MetaData metaData) {
        logger.info(metaData.getCaseName());
        logger.debug("\n" + metaData.getExpect().toString() + "\n" + metaData.getActual().toString());
        JsonDiffOption.setDefaultJsonFramework(ImplType.FASTJSON);
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .detectDiff(JSON.toJSONString(metaData.getExpect()), JSON.toJSONString(metaData.getActual()));
        if (metaData.getRet() != null) {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent(metaData.getRet().toString()));
        } else {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent("{\"match\":true}"));
        }
    }

        @Test(dataProvider = "err", dataProviderClass = ArrayDataFactory.class)
        public void noOptionErrTest(MetaData metaData) {
        logger.info(metaData.getCaseName());
        logger.debug("\n" + metaData.getExpect().toString() + "\n" + metaData.getActual().toString() + "\n" + metaData.getOption());
        JsonDiffOption.setDefaultJsonFramework(ImplType.FASTJSON);
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .detectDiff(JSON.toJSONString(metaData.getExpect()), JSON.toJSONString(metaData.getActual()));
        Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent(metaData.getRet().toString()));
    }

        @Test(dataProvider = "optionRight", dataProviderClass = ArrayDataFactory.class)
        public void optionRight(MetaData metaData) {
        logger.info(metaData.getCaseName());
        logger.debug("\n" + metaData.getExpect().toString() + "\n" + metaData.getActual().toString() + "\n");
        JsonDiffOption.setDefaultJsonFramework(ImplType.FASTJSON);
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(metaData.getOption())
                .detectDiff(JSON.toJSONString(metaData.getExpect()), JSON.toJSONString(metaData.getActual()));
        if (metaData.getRet() != null) {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent(metaData.getRet().toString()));
        } else {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent("{\"match\":true}"));
        }
    }

        @Test(dataProvider = "optionErr", dataProviderClass = ArrayDataFactory.class)
        public void optionErr(MetaData metaData) {
        logger.info(metaData.getCaseName());
        logger.debug("\n" + metaData.getExpect().toString() + "\n" + metaData.getActual().toString() + "\n");
        JsonDiffOption.setDefaultJsonFramework(ImplType.FASTJSON);
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(metaData.getOption())
                .detectDiff(JSON.toJSONString(metaData.getExpect()), JSON.toJSONString(metaData.getActual()));
        if (metaData.getRet() != null) {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent(metaData.getRet().toString()));
        } else {
            Assert.assertEquals(FormatContent.formatComparisonContent(jsonCompareResult), FormatContent.formatComparisonContent("{\"match\":true}"));
        }
    }
}
