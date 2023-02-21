package me.codeleep.jsondiff.utils;

import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.JsonCompareResult;

import static me.codeleep.jsondiff.common.model.Constant.DATA_INCONSISTENT;
import static me.codeleep.jsondiff.common.model.Constant.DATA_TYPE_INCONSISTENT;

/**
 * @author: codeleep
 * @createTime: 2023/02/20 20:01
 * @description:
 */
public class JsonCompareUtil {


    // 处理基础类型
    public static JsonCompareResult handlePrimitiveType(Object expectObject, Object actualObject, String path) {
        JsonCompareResult jsonCompareResult =  new JsonCompareResult();
        if (expectObject == null && actualObject == null) {
            jsonCompareResult.setMatch(true);
            return jsonCompareResult;
        }
        // 类型不一样
        if (!ClassUtil.isSameClass(expectObject, actualObject)) {
            jsonCompareResult.setMatch(false);
            Defects defects = new Defects()
                    .setActual(actualObject)
                    .setExpect(expectObject)
                    .setIndexPath(path)
                    .setIllustrateTemplate(DATA_TYPE_INCONSISTENT, expectObject == null ? null : expectObject.getClass().getName(), actualObject.getClass().getName());
            jsonCompareResult.addDefects(defects);
            return jsonCompareResult;
        }
        // 判断值是否一致
        if (expectObject.equals(actualObject)) {
            jsonCompareResult.setMatch(true);
            return jsonCompareResult;
        }else {
            jsonCompareResult.setMatch(false);
            Defects defects = new Defects()
                    .setActual(actualObject)
                    .setExpect(expectObject)
                    .setIndexPath(path)
                    .setIllustrateTemplate(DATA_INCONSISTENT, String.valueOf(expectObject), String.valueOf(actualObject));
            jsonCompareResult.addDefects(defects);
        }
        return jsonCompareResult;
    }


}
