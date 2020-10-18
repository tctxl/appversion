package com.opdar.appversion.base;

import com.alibaba.fastjson.JSON;
import com.opdar.mote.web.interceptor.IErrorHandler;
import com.opdar.mote.web.router.validator.ValidateException;

public class ErrorPrinterHandler implements IErrorHandler {

    @Override
    public byte[] handle(Throwable throwable) {
        throwable.printStackTrace();
        Result result = new Result();
        result.setCode(0xffff);
        if(throwable instanceof ValidateException){
            result.setMsg(((ValidateException) throwable).getErrMsg());
        }else if(throwable instanceof ErrCodeException){
            result.setCode(((ErrCodeException) throwable).getCode());
            result.setMsg(((ErrCodeException) throwable).getMsg());
        }else{
            result.setMsg(throwable.getMessage());
        }
        return JSON.toJSONBytes(result);
    }
}
