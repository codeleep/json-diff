package me.codeleep.jsondiff.test.dataFactory;

import me.codeleep.jsondiff.test.model.MetaData;
import org.testng.annotations.DataProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static me.codeleep.jsondiff.test.model.Constant.*;

/**
 * @author: chenfeng
 * @createTime: 2024/4/21
 * @description: 加载自定义比较器的数据类
 */
public class HandleFactoryDataFactory {
    private static final Map<String, Integer> maxMap = new HashMap<>();
    public static Map<String, ArrayList<MetaData>> arrayData = new HashMap<>();

    static {
        FactoryUtil.load(HANDLE_PATH,LOAD_DATE_NAME,maxMap,arrayData);
    }
    @DataProvider(name = "right")
    public Object[] rightData() {
        return arrayData.get("right").toArray(new MetaData[maxMap.get("right")]);
    }

    @DataProvider(name = "err")
    public Object[] errData() {
        return arrayData.get("err").toArray(new MetaData[maxMap.get("err")]);
    }
}
