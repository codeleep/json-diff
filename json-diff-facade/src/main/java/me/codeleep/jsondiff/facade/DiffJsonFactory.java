package me.codeleep.jsondiff.facade;

import me.codeleep.jsondiff.exception.JsonDiffException;
import me.codeleep.jsondiff.model.json.DiffJsonArray;
import me.codeleep.jsondiff.model.json.DiffJsonObject;
/**
 * @author: codeleep
 * @createTime: 2022/09/29 23:56
 * @description: 获取实例的工厂
 */
public class DiffJsonFactory {


    public static DiffJsonObject buildObject(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        Class<?> objectImplClass = getObjectImplClass();
        try {
            DiffJsonObject diffJsonObject = (DiffJsonObject)objectImplClass.newInstance();
            diffJsonObject.build(text);
            return diffJsonObject;
        }catch (Exception e) {
            throw new JsonDiffException("实例创建失败");
        }
    }


    public static DiffJsonArray buildArray(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        Class<?> objectImplClass = getObjectImplClass();
        try {
            DiffJsonArray diffJsonArray = (DiffJsonArray)objectImplClass.newInstance();
            diffJsonArray.build(text);
            return diffJsonArray;
        }catch (Exception e) {
            throw new JsonDiffException("实例创建失败");
        }
    }


    /**
     * 找到引入的实现类
     * @return 实现的class
     */
    private static Class<?> getObjectImplClass() {
        try {
            return Class.forName("me.codeleep.jsondiff.jsonimpl.fastjson.FastJsonObject");
        }catch (Exception e) {

        }
        // TODO 其他JSON框架的实现
        throw new JsonDiffException("未找到合适的DiffJsonObject的实现类");
    }

    /**
     * 找到引入的实现类
     * @return 实现的class
     */
    private static Class<?> getArrayImplClass() {
        try {
            return Class.forName("me.codeleep.jsondiff.jsonimpl.fastjson.FastJsonArray");
        }catch (Exception e) {

        }
        // TODO 其他JSON框架的实现
        throw new JsonDiffException("未找到合适的DiffJsonArray的实现类");
    }
}
