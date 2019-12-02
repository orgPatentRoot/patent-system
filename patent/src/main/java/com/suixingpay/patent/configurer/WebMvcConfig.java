package com.suixingpay.patent.configurer;

import com.suixingpay.patent.interceptor.MyHandlerIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private MyHandlerIntercepter myHandlerIntercepter;

    //不需要拦截的路径
    private final String[] notLoginInterceptPaths = {"/user/login","/file/upload"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(myHandlerIntercepter).addPathPatterns("/**")
                       .excludePathPatterns(notLoginInterceptPaths);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**")
                .addResourceLocations("classpath:../public/");
        super.addResourceHandlers(registry);
    }



}
