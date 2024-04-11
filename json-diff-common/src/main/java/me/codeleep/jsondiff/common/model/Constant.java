package me.codeleep.jsondiff.common.model;

/**
 * @author: codeleep
 * @createTime: 2022/11/26 12:30
 * @description: 常量池
 */
public class Constant {


    /**
     **************************************************************************
     *  遍历所需常量
     ***************************************************************************
     */
    public static final String PATH_ROOT = "$";
    public static final String SIGN = ".";
    public static final String JOIN_SPILT = "@_^_@";
    public static final String NULL = "null";



    /**
     **************************************************************************
     *  提示信息
     ***************************************************************************
     */
    // 预期和实际不一致
    public static final String DATA_INCONSISTENT = "The expect('%s') data is inconsistent with the actual('%s') data";
    // 预期的值类型和实际的值类型不一致
    public static final String DATA_TYPE_INCONSISTENT = "The expect type ('%s') is inconsistent with the actual type ('%s')";

    public static final String INCONSISTENT_ARRAY_LENGTH = "The expect array length ('%s') is inconsistent with the actual array length ('%s')";

    // 发现异常
    public static final String FINDING_ANOMALY = "expect('%s') are found when comparing the actual('%s') and expected results。msg: %s";

    // 只存在于一个集合的key
    public static final String SEPARATE_KEY = "Only one set of keys exists expect('%s'),actual('%s')";

}
