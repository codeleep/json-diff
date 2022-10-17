package me.codeleep.jsondiff.test.dataFactory;

import me.codeleep.jsondiff.model.MetaData;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: fen
 * @createTime: 2022/08/17 23:20
 * @description: 字典数据工厂，用于初始化数据
 */
public class ObjectDataFactory {
    private static String path = "object/MultObjects.json";
    private static String[] loadDataName = {"err", "right","optionRight","optionErr"};
    private static Map<String, Integer> maxMap = new HashMap<>();
    public static Map<String, ArrayList<MetaData>> arrayData = new HashMap<>();

    static {
        FactoryUtil.load(path,loadDataName,maxMap,arrayData);
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
