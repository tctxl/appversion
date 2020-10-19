package com.opdar.appversion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opdar.appversion.background.Constants;
import com.opdar.appversion.background.EntryUtils;
import com.opdar.appversion.base.ErrCodeException;
import com.opdar.mote.web.Mote;
import com.opdar.mote.web.base.Context;
import com.opdar.mote.web.cli.ICommand;
import com.opdar.mote.web.configuration.CommandRegistry;
import com.opdar.mote.web.configuration.CorsRegistry;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.servlet.DispatcherType;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

@ComponentScan(basePackages = {"com.opdar.appversion"})
public class ApiApplication {
    public static void main(String[] args)  {
        Context.putResourceMapping("/upload", "./upload");
        Context.putResourceMapping("/apk", "./apk");
        Context.putResourceMapping("/static", "classpath:/templates/dash/static");

        Mote mote = Mote.getInstance();
        CorsRegistry.get().setAllowOrigin("http://127.0.0.1:8080");
        CommandRegistry.get().addCommand("entry","e", new ICommand() {
            @Override
            public void run(String[] s) {
                if(s[0].equals("show")){
                    File file = new File(Constants.ENTRY);
                    if(file.exists()){
                        try {
                            String entry = FileUtils.readFileToString(file);
                            System.out.println(entry);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("未生成入口点");
                    }
                }
            }
        });
        mote.setServiceName("appversion");
        mote.run(10005, ApiApplication.class);

    }
}
