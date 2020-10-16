package com.opdar.appversion.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrCodeException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(ErrCodeException.class);

    private int code;
    private String msg;
    private Object data;

    public ErrCodeException() {
        this.code = 0xFFFF;
        this.msg = "未知错误";
    }
    public ErrCodeException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ErrCodeException(String msg) {
        this.code = 0xffff;
        this.msg = msg;
    }

    public ErrCodeException(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }

    public ErrCodeException(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
