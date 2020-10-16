package com.opdar.appversion.config;

import com.opdar.appversion.background.UserEntity;
import com.opdar.mote.web.session.ISessionManager;
import com.opdar.mote.web.session.MemorySessionManager;
import com.opdar.mote.web.view.FreemarkerViewTemplate;
import com.opdar.mote.web.view.ViewTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
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
