package com.opdar.appversion;

import org.eclipse.jetty.server.Response;

import javax.servlet.*;
import java.io.IOException;

public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ((Response) servletResponse).setHeader("Access-Control-Allow-Origin", "http://192.168.1.178:8081");
//        ((Response) servletResponse).setHeader("Access-Control-Allow-Origin", "http://zstest.askdr.cn");
//        ((Response) servletResponse).setHeader("Access-Control-Allow-Origin", "http://192.168.2.101:8001");
        ((Response) servletResponse).setHeader("Access-Control-Allow-Credentials", "true");
        ((Response) servletResponse).setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        ((Response) servletResponse).setHeader("Access-Control-Max-Age", "3600"); //设置过期时间
        ((Response) servletResponse).setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
