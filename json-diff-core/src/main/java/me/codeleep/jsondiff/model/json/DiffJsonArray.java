package me.codeleep.jsondiff.model.json;

/**
 * 抽象json数组
 */
public interface DiffJsonArray {

    int size();

    Object[] toArray();

    DiffJsonArray build(String text);
}
