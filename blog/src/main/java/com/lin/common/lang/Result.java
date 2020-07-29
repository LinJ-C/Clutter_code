package com.lin.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Joe
 * @created: 2020/7/25 19:28
 */
@Data
public class Result implements Serializable {


    private int code;
    private String msg;
    private Object data;

    public static Result succ(Object data){
        return succ(200,"操作成功",data);
    }

    public static Result succ(int code,String msg,Object data){
        Result m = new Result();
        m.setCode(code);
        m.setMsg(msg);
        m.setData(data);
        return m;
    }

    public static Result fail(String msg){
        return succ(400,msg, null);
    }
    public static Result fail(String msg,Object data){
        return succ(400,"数据错误", data);
    }
    public static Result fail(int code,String msg,Object data) {
        Result m = new Result();
        m.setCode(code);
        m.setMsg(msg);
        m.setData(data);
        return m;
    }
}

