package me.codeleep.jsondiff.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.DefaultJsonDifference;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.model.MetaData;
import me.codeleep.jsondiff.test.dataFactory.ArrayDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
/**
 * @author: chenfeng
 * @createTime: 2023/3/2 21:55
 * @description: 数组类型的测试类
 */
public class MultAllArrayTest {

    @Test(dataProvider = "right", dataProviderClass = ArrayDataFactory.class)
    public void noOptionRightTest(MetaData metaData) {
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .detectDiff((JSONArray) metaData.getExpect(), (JSONArray) metaData.getActual());
        System.out.println(JSON.toJSONString(metaData.getRet()) + "\n" + JSON.toJSONString(jsonCompareResult));
        if (metaData.getRet() != null)
            Assert.assertEquals(JSON.toJSONString(metaData.getRet()), JSON.toJSONString(jsonCompareResult));
        else
            Assert.assertEquals("{\"match\":true}", JSON.toJSONString(jsonCompareResult));
    }

    @Test(dataProvider = "err", dataProviderClass = ArrayDataFactory.class)
    public void noOptionErrTest(MetaData metaData) {
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .detectDiff((JSONArray) metaData.getExpect(), (JSONArray) metaData.getActual());
        System.out.println(JSON.toJSONString(metaData.getRet()) + "\n" + JSON.toJSONString(jsonCompareResult));
        Assert.assertEquals(JSON.toJSONString(metaData.getRet()), JSON.toJSONString(jsonCompareResult));

    }

    @Test(dataProvider = "optionRight", dataProviderClass = ArrayDataFactory.class)
    public void optionRight(MetaData metaData) {
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(metaData.getOption())
                .detectDiff((JSONArray) metaData.getExpect(), (JSONArray) metaData.getActual());
        System.out.println(JSON.toJSONString(metaData.getRet()) + "\n" + JSON.toJSONString(jsonCompareResult));
        if (metaData.getRet() != null)
            Assert.assertEquals(JSON.toJSONString(metaData.getRet()), JSON.toJSONString(jsonCompareResult));
        else
            Assert.assertEquals("{\"match\":true}", JSON.toJSONString(jsonCompareResult));
    }

    @Test(dataProvider = "optionErr", dataProviderClass = ArrayDataFactory.class)
    public void optionErr(MetaData metaData) {
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(metaData.getOption())
                .detectDiff((JSONArray) metaData.getExpect(), (JSONArray) metaData.getActual());
        System.out.println(JSON.toJSONString(metaData.getRet()) + "\n" + JSON.toJSONString(jsonCompareResult));
        Assert.assertEquals(JSON.toJSONString(metaData.getRet()), JSON.toJSONString(jsonCompareResult));
    }
}
