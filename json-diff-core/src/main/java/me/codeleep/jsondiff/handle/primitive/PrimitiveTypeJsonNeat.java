package me.codeleep.jsondiff.handle.primitive;

import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.utils.ClassUtil;

import static me.codeleep.jsondiff.common.model.Constant.DATA_INCONSISTENT;
import static me.codeleep.jsondiff.common.model.Constant.DATA_TYPE_INCONSISTENT;

/**
 * @author: codeleep
 * @createTime: 2023/02/22 22:53
 * @description: 处理基础类型
 */
public class PrimitiveTypeJsonNeat extends AbstractPrimitiveJsonNeat {

    @Override
    public JsonCompareResult diff(Object expect, Object actual, String path) {
        if (!ClassUtil.isPrimitiveType(expect) || !ClassUtil.isPrimitiveType(actual)) {
            throw new JsonDiffException("类型调用错误");
        }

        JsonCompareResult result = new JsonCompareResult();
        // 都为null
        if (expect == null && actual == null) {
            return result;
        }
        // class不一致
        if (!ClassUtil.isSameClass(expect, actual)) {
            Defects defects = new Defects()
                    .setActual(actual)
                    .setExpect(expect)
                    .setIndexPath(path)
                    .setIllustrateTemplate(DATA_TYPE_INCONSISTENT, expect == null ? null : expect.getClass().getName(), actual == null ? null : actual.getClass().getName());
            result.addDefects(defects);
            return result;
        }

        // 值一样
        if (expect.equals(actual)) {
            return result;
        }

        // 值不一致
        Defects defects = new Defects()
                .setActual(actual)
                .setExpect(expect)
                .setIndexPath(path)
                .setIllustrateTemplate(DATA_INCONSISTENT, String.valueOf(expect), String.valueOf(actual));
        result.addDefects(defects);
        return result;
    }

}
