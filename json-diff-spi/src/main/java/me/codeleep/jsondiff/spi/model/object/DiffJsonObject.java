package me.codeleep.jsondiff.spi.model.object;


/**
 * @author: codeleep
 * @createTime: 2022/11/14 15:59
 * @description: 抽象Json对象接口
 */
public interface DiffJsonObject {

    /**
     * 如果遇到元素是对象。按照key字典序进行序列化
     * @return 序列话string
     */
    String toJsonString();

}
