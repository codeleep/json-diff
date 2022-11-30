package me.codeleep.jsondiff.temporary;

import me.codeleep.jsondiff.DefaultJsonDifference;
import me.codeleep.jsondiff.facade.DiffJsonFactory;
import me.codeleep.jsondiff.model.DIFFJSON;
import me.codeleep.jsondiff.model.JsonCompareResult;
import me.codeleep.jsondiff.model.JsonComparedOption;
import me.codeleep.jsondiff.spi.model.object.DiffJsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: codeleep
 * @createTime: 2022/09/01 15:44
 * @description: 临时的测试
 */
public class Test {

    @org.testng.annotations.Test
    public void test1() {
        String array1 = "{\"Object\":{\"code\":\"0\",\"message\":\"success\"},\"code\":\"0\",\"message\":\"success\"}";
        String array2 = "{\"Object\":{\"code\":\"0\",\"message\":\"failure\"},\"message\":\"success\",\"timestamp\":\"1614301293\"}";
        DiffJsonObject obj1 = DiffJsonFactory.buildObject(array1);
        DiffJsonObject obj2 = DiffJsonFactory.buildObject(array2);
        List list = new ArrayList<>();
        list.add("root.Object.message");
        // 构建配置对象
        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true).setIgnorePath(list);
        // 初始化工具
        JsonCompareResult jsonCompareResult = new DefaultJsonDifference()
                .option(jsonComparedOption)
                // 对比
                .detectDiff(obj1, obj2);
        System.out.println(DIFFJSON.toJSONString(jsonCompareResult));
    }

    @org.testng.annotations.Test
    public void test2() {
        String array1 = "{\"Object\":{\"code\":\"0\",\"message\":\"success\"},\"code\":\"0\",\"message\":\"success\"}";
        String array2 = "{\"Object\":{\"code\":\"0\",\"message\":\"failure\"},\"message\":\"success\",\"timestamp\":\"1614301293\"}";
        DiffJsonObject obj1 = DiffJsonFactory.buildObject(array1);
        DiffJsonObject obj2 = DiffJsonFactory.buildObject(array2);
    }

}
