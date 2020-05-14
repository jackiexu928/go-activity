package com.jackie.goactivity.process;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-04-29
 */
@Data
public class Context<P,M> implements Serializable {
    //入参
    private P param;
    //出参
    private M result;
    //类名
    private String className;
    //方法名
    private String methodName;

    //是否成功
    private boolean success;

    //错误码
    private String errorCode;
    //错误信息
    private String errorMsg;

    public Context(){

    }

    public Context(P param, M result, String className, String methodName){
        this.param=param;
        this.result=result;
        this.className=className;
        this.methodName=methodName;
    }

}
