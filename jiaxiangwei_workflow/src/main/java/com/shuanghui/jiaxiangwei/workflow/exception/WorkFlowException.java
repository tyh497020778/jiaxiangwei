package com.shuanghui.jiaxiangwei.workflow.exception;

/**
 * Created by administration on 2019/8/10.
 */
public class WorkFlowException extends RuntimeException{
    int code=500;
    Object data;
    public WorkFlowException(String msg){
        super(msg);
    }
    public WorkFlowException(int code,String msg){
        super(msg);
        this.code=code;
    }
    public WorkFlowException(int code,String msg,Object data){
        super(msg);
        this.code=code;
        this.data=data;
    }
}
