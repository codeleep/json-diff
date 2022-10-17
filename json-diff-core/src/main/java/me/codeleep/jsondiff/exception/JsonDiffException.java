package me.codeleep.jsondiff.exception;


public class JsonDiffException extends RuntimeException {


    //异常信息
    private String message;

    //构造函数
    public JsonDiffException(String message){
        super(message);
        this.message = message;
    }

}
