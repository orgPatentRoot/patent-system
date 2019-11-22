package com.suixingpay.patent.configurer;

import com.suixingpay.patent.interceptor.MyHandlerIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    MyHandlerIntercepter myHandlerIntercepter;

    //不需要拦截的路径
    final String[] notLoginInterceptPaths = {"/user/login","/patent/test"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myHandlerIntercepter).addPathPatterns("/**")
                .excludePathPatterns(notLoginInterceptPaths);
    }

}
