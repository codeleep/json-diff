package me.codeleep.jsondiff.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.DefaultJsonDifference;
import me.codeleep.jsondiff.model.JsonCompareResult;
import me.codeleep.jsondiff.model.MetaData;
import me.codeleep.jsondiff.test.dataFactory.ArrayDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MultAllArrayTest {

    @Test(dataProvider = "right",dataProviderClass = ArrayDataFactory.class)
    public void noOptionRightTest(MetaData metaData){
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .detectDiff((JSONArray) metaData.getExpect(), (JSONArray) metaData.getActual());
        Assert.assertEquals("{\"match\":true}",JSON.toJSONString(jsonCompareResult));
    }
    @Test(dataProvider = "err",dataProviderClass = ArrayDataFactory.class)
    public void noOptionErrTest(MetaData metaData){
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .detectDiff((JSONArray) metaData.getExpect(), (JSONArray) metaData.getActual());
        Assert.assertEquals(JSON.toJSONString(metaData.getRet()), JSON.toJSONString(jsonCompareResult));
    }
    @Test(dataProvider = "optionRight",dataProviderClass = ArrayDataFactory.class)
    public void optionRight(MetaData metaData){
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(metaData.getOption())
                .detectDiff((JSONArray) metaData.getExpect(), (JSONArray) metaData.getActual());
        Assert.assertEquals(JSON.toJSONString(metaData.getRet()), JSON.toJSONString(jsonCompareResult));
    }
    @Test(dataProvider = "optionErr",dataProviderClass = ArrayDataFactory.class)
    public void optionErr(MetaData metaData){
        DefaultJsonDifference defaultJsonDifference = new DefaultJsonDifference();
        JsonCompareResult jsonCompareResult = defaultJsonDifference
                .option(metaData.getOption())
                .detectDiff((JSONArray) metaData.getExpect(), (JSONArray) metaData.getActual());
        Assert.assertEquals(JSON.toJSONString(metaData.getRet()), JSON.toJSONString(jsonCompareResult));
    }
}
