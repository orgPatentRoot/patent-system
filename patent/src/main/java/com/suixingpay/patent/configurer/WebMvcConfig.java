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
    private final String[] notLoginInterceptPaths = {"/user/login"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//       registry.addInterceptor(myHandlerIntercepter).addPathPatterns("/**")
//                       .excludePathPatterns(notLoginInterceptPaths);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
/*        registry.addResourceHandler("/public/**").addResourceLocations("file:D:/[工作]/培训/项目练习/星火四期专利需求/patent-system/public");
        super.addResourceHandlers(registry);*/
    }



}
