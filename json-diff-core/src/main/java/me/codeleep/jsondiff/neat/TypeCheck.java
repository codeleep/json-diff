package me.codeleep.jsondiff.neat;

/**
 * @author: codeleep
 * @createTime: 2023/02/22 23:18
 * @description: 类型校验
 */
public interface TypeCheck {

    /**
     * 校验类型是否一致
     * @param expect
     * @param actual
     * @return
     */
    boolean check(Object expect, Object actual);

}
