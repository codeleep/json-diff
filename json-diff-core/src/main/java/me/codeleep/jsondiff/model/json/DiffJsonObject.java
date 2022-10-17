package me.codeleep.jsondiff.model.json;

import java.util.Set;

/**
 * 抽象Json对象
 */
public interface DiffJsonObject {

    Object get(String key);
    Set<String> keySet();
    String getString(String key);

    Long getLong(String key);
    Boolean getBoolean(String key);
    DiffJsonArray getDiffJsonArray(String key);

    int size();

    DiffJsonObject build(String text);
}
