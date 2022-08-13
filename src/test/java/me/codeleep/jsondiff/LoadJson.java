package me.codeleep.jsondiff;

import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.model.MetaData;
import me.codeleep.jsondiff.utils.ResourceUtils;

import java.io.File;

/**
 * @author: codeleep
 * @createTime: 2022/08/08 23:12
 * @description: 加载数据
 */
public class LoadJson {

    public MetaData load(String expectPath, String actualPath) {
        MetaData metaData = new MetaData();
        String expectContent = ResourceUtils.loadResourceLine(expectPath);
        metaData.setExpect(JSON.parse(expectContent));
        String actualContent = ResourceUtils.loadResourceLine(actualPath);
        metaData.setActual(JSON.parse(actualContent));
        return metaData;
    }

}
