package me.codeleep.jsondiff.common.model;

/**
 * @author: codeleep
 * @createTime: 2023/02/19 20:05
 * @description: key 映射
 */
public class MappingKey {

    /**
     * 期望的key
     */
    private String expectKey;

    /**
     * 实际key
     */
    private String actualKey;

    public MappingKey(String expectKey, String actualKey) {
        this.expectKey = expectKey;
        this.actualKey = actualKey;
    }

    public String getExpectKey() {
        return expectKey;
    }

    public String getActualKey() {
        return actualKey;
    }

}
