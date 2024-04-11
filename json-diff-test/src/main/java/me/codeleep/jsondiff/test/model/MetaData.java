package me.codeleep.jsondiff.test.model;

import me.codeleep.jsondiff.core.config.JsonComparedOption;

/**
 * @author: chenfeng
 * @createTime: 2023/3/2 21:55
 * @description: 测试源数据对象
 */
public class MetaData {

    private Object expect;

    private Object ret;
    private Object actual;
    private String caseName;
    private JsonComparedOption option;
    public MetaData(){}
    public MetaData(String caseName,Object expect,Object actual){
        this.expect = expect;
        this.actual = actual;
        this.caseName= caseName;
    }
    public MetaData(String caseName,Object expect,Object actual,Object ret,JsonComparedOption option){
        this.actual = actual;
        this.expect = expect;
        this.ret = ret;
        this.option = option;
        this.caseName = caseName;
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
    public void setCaseName(String caseName){
        this.caseName = caseName;
    }
    public String getCaseName(){
        return this.caseName;
    }
}
