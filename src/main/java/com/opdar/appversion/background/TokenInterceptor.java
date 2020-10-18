package com.opdar.appversion.background;

import com.opdar.appversion.base.ErrCodeException;
import com.opdar.mote.web.interceptor.AbstractInterceptor;
import com.opdar.mote.web.session.ISessionManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

public class TokenInterceptor extends AbstractInterceptor implements ApplicationContextAware {

    private ISessionManager<UserEntity> sessionManager;

    @Override
    public boolean preHandle() {
        String token = (String) getHeaders().get("Authorization");
        if(StringUtils.isEmpty(token) && getParameters().containsKey("token")){
            token = ((String[])getParameters().get("token"))[0];
        }
        if(!StringUtils.isEmpty(token)){
            UserEntity user = sessionManager.get(token);
            if(user != null){
                return true;
            }
        }
        throw new ErrCodeException(305,Constants.ErrorText.TOKEN_EXPIRE);
    }

    @Override
    public boolean postHandle() {
        return true;
    }

    @Override
    public void afterCompletion() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sessionManager = applicationContext.getBean(ISessionManager.class);
    }
}
