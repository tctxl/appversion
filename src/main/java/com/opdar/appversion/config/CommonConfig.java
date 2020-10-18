package com.opdar.appversion.config;

import com.opdar.appversion.background.UserEntity;
import com.opdar.mote.web.session.ISessionManager;
import com.opdar.mote.web.session.MemorySessionManager;
import com.opdar.mote.web.view.FreemarkerViewTemplate;
import com.opdar.mote.web.view.ViewTemplate;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public DiskFileItemFactory fif() {
        DiskFileItemFactory fif = new DiskFileItemFactory();
        fif.setSizeThreshold(1024000);
        return fif;
    }

    @Bean
    public ServletFileUpload sfu(DiskFileItemFactory fif) {
        ServletFileUpload sfu = new ServletFileUpload();
        sfu.setFileItemFactory(fif);
        return sfu;
    }

    @Bean
    public ViewTemplate viewTemplate() {
        FreemarkerViewTemplate vt = new FreemarkerViewTemplate();
        vt.setPrefix("classpath:/dash/");
        return vt;
    }

    @Bean
    public ISessionManager<UserEntity> sessionManager() {
        return new MemorySessionManager<UserEntity>();
    }
}
