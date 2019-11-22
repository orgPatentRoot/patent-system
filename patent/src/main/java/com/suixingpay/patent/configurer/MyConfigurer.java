package com.suixingpay.patent.configurer;


import com.suixingpay.patent.interceptor.BaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyConfigurer extends WebMvcConfigurationSupport {

    @Autowired BaseInterceptor baseInterceptor;


    final String[] notLoginInterceptPaths = {"/user/login"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);
    }
}
