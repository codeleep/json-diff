package me.codeleep.jsondiff.test.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @author: chenfeng
 * @createTime: 2023/3/2 21:55
 * @description: 加载resource下面的数据工具类
 */
public class ResourceUtils {

    /**
     * 加载resource目录中的文件
     * @param resourcePath 资源目录下相对路径
     * @return 按行封装成list
     */
    public static String loadResourceLine(String resourcePath) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(resourcePath);
        Objects.requireNonNull(inputStream);
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error parsing file.", e);
        }
        return content.toString();
    }


}
