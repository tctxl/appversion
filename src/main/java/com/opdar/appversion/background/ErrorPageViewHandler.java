package com.opdar.appversion.background;

import com.alibaba.fastjson.JSON;
import com.opdar.appversion.base.ErrCodeException;
import com.opdar.appversion.base.Result;
import com.opdar.mote.web.base.Context;
import com.opdar.mote.web.interceptor.IErrorHandler;
import com.opdar.mote.web.router.validator.ValidateException;
import com.opdar.mote.web.view.ViewTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class ErrorPageViewHandler implements IErrorHandler, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public byte[] handle(Throwable throwable) {
        ViewTemplate viewTemplate = applicationContext.getBean(ViewTemplate.class);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(throwable instanceof ErrCodeException){
            String msg = ((ErrCodeException) throwable).getMsg();
            Context.putAttribute("msg",msg);
        }else{
            Context.putAttribute("msg","未知错误");
        }
        viewTemplate.render("error",baos);
        return baos.toByteArray();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
