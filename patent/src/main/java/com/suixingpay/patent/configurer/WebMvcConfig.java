package com.suixingpay.patent.configurer;

import com.suixingpay.patent.interceptor.MyHandlerIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private MyHandlerIntercepter myHandlerIntercepter;

    //不需要拦截的路径
    private final String[] notLoginInterceptPaths = {"/user/login", "/history/test"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(myHandlerIntercepter).addPathPatterns("/**")
        ////                .excludePathPatterns(notLoginInterceptPaths);
    }

}
