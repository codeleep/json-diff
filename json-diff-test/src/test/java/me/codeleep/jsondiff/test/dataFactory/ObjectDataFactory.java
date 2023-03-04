package me.codeleep.jsondiff.test.dataFactory;

import me.codeleep.jsondiff.test.model.MetaData;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static me.codeleep.jsondiff.test.model.Constant.LOAD_DATE_NAME;
import static me.codeleep.jsondiff.test.model.Constant.OBJECT_PATH;

/**
 * @author: chenfeng
 * @createTime: 2023/3/2 21:55
 * @description: 字典数据工厂，用于初始化数据
 */
public class ObjectDataFactory {
    private static Map<String, Integer> maxMap = new HashMap<>();
    public static Map<String, ArrayList<MetaData>> arrayData = new HashMap<>();

    static {
        FactoryUtil.load(OBJECT_PATH,LOAD_DATE_NAME,maxMap,arrayData);
    }

    @DataProvider(name = "right")
    public Object[] rightData() {
        return arrayData.get("right").toArray(new MetaData[maxMap.get("right")]);
    }

    @DataProvider(name = "err")
    public Object[] errData() {
        return arrayData.get("err").toArray(new MetaData[maxMap.get("err")]);
    }

    @DataProvider(name = "optionRight")
    public Object[] optionRightData(){
        return arrayData.get("optionRight").toArray(new MetaData[maxMap.get("optionRight")]);
    }
    @DataProvider(name = "optionErr")
    public Object[] optionErrData(){
        return arrayData.get("optionErr").toArray(new MetaData[maxMap.get("optionErr")]);
    }
}
