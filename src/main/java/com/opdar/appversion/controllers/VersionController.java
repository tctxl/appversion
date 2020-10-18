package com.opdar.appversion.controllers;

import com.opdar.appversion.ApiApplication;
import com.opdar.appversion.base.ErrCodeException;
import com.opdar.appversion.entity.AppChannelEntity;
import com.opdar.appversion.entity.AppEntity;
import com.opdar.appversion.service.AppService;
import com.opdar.mote.web.annotations.web.Request;
import com.opdar.mote.web.base.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

@Controller
public class VersionController {


    @Autowired
    private AppService appService;

    @Request(value = "/api/version/check", format = Request.Format.JSON)
    public AppChannelEntity check(String ver,String channel,Long appId) {
        if(StringUtils.isEmpty(ver) || StringUtils.isEmpty(channel) || StringUtils.isEmpty(appId)){
            throw new ErrCodeException("参数不能为空");
        }
        return appService.checkVersion(ver,channel,appId);
    }

    @Request(value = "/s/{{appId}}",restful = true, format = Request.Format.VIEW)
    public String check(Long appId) {
        if(StringUtils.isEmpty(appId)){
            throw new ErrCodeException("未找到该应用");
        }
        AppEntity app = appService.findLastAppVersion(appId);
        if(app.getChannels().size() == 0){
            throw new ErrCodeException("未找到该应用");
        }
        Context.putAttribute("app",app);
        return "app";
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApiApplication.class);
        VersionController versionController = context.getBean(VersionController.class);
        try{
            AppChannelEntity v = versionController.check("1.0.0.1", "normal", 1000L);
            System.out.println(v);
        }catch (ErrCodeException e){
            System.out.println(e.getMsg());
        }
    }
}
