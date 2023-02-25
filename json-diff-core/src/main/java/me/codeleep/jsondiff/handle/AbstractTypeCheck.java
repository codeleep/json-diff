package me.codeleep.jsondiff.handle;

import me.codeleep.jsondiff.neat.TypeCheck;
import me.codeleep.jsondiff.utils.ClassUtil;

/**
 * @author: codeleep
 * @createTime: 2023/02/22 23:19
 * @description: 抽象类型check
 */
public class AbstractTypeCheck implements TypeCheck {

    @Override
    public boolean check(Object expect, Object actual) {
        return ClassUtil.isSameClass(expect, actual);
    }

}
