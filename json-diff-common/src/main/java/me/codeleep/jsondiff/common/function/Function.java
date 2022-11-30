package me.codeleep.jsondiff.common.function;

import java.util.HashSet;
import java.util.Stack;

@FunctionalInterface
public interface Function {

    /**
     * 根据当前的路径。判断是否需要进行指定key联系对象
     * @param exPath 期望对象的路径
     * @param acPath 真实对象的路径
     * @return 返回一个key的集合
     */
    HashSet<String> apply(String exPath, String acPath);
}

