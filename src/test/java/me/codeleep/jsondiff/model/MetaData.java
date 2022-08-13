package me.codeleep.jsondiff.model;

/**
 * @author: codeleep
 * @createTime: 2022/08/08 23:14
 * @description: 测试源数据对象
 */
public class MetaData {

    private Object expect;


    private Object actual;

    public Object getExpect() {
        return expect;
    }

    public void setExpect(Object expect) {
        this.expect = expect;
    }

    public Object getActual() {
        return actual;
    }

    public void setActual(Object actual) {
        this.actual = actual;
    }
}
