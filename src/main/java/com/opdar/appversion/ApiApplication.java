package com.opdar.appversion;

import com.opdar.mote.web.Mote;
import com.opdar.mote.web.base.Context;
import com.opdar.mote.web.configuration.CorsRegistry;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

@ComponentScan(basePackages = {"com.opdar.appversion"})
public class ApiApplication {
    public static void main(String[] args)  {

        Mote mote = Mote.getInstance();
        CorsRegistry.get().setAllowOrigin("http://192.168.1.178:8080");
        mote.setServiceName("appversion");
        mote.run(10005, ApiApplication.class);

    }
}
