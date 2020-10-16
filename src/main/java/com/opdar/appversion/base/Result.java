package com.opdar.appversion.base;

import java.sql.Timestamp;

public class Result<T> {
    private String msg = "操作成功";
    private int code;
    private T data;
    private Timestamp systemTime = new Timestamp(System.currentTimeMillis());

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Timestamp getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Timestamp systemTime) {
        this.systemTime = systemTime;
    }
}
