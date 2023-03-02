package me.codeleep.jsondiff.model;

import me.codeleep.jsondiff.common.model.JsonComparedOption;

/**
 * @author: chenfeng
 * @createTime: 2023/3/2 21:55
 * @description: 测试源数据对象
 */
public class MetaData {

    private Object expect;

    private Object ret;
    private Object actual;
    private JsonComparedOption option;
    public MetaData(){}
    public MetaData(Object expect,Object actual){
        this.expect = expect;
        this.actual = actual;
    }
    public MetaData(Object expect,Object actual,Object ret,JsonComparedOption option){
        this.actual = actual;
        this.expect = expect;
        this.ret = ret;
        this.option = option;
    }
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

    public Object getRet() {
        return ret;
    }

    public void setRet(Object ret) {
        this.ret = ret;
    }

    public JsonComparedOption getOption(){
        return option;
    }
    public void setOption(JsonComparedOption option) {
        this.option = option;
    }
}
