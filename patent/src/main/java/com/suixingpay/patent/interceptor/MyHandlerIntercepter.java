package com.suixingpay.patent.interceptor;


import com.suixingpay.patent.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class MyHandlerIntercepter implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //获取session
        HttpSession session = request.getSession();
        //获取用户
        User user = (User) session.getAttribute("user");
        //判断是否登录
        if (user != null) {
            return true;
        } else {
            //没登录则返回登录页
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            response.sendRedirect(request.getContextPath() + "/user/nologin");
            return false;
        }

        /*logger.info("进入LoginInterceptor拦截器==============");
        String basePath = request.getContextPath();
        String path = request.getRequestURI();
        logger.info("basePath:" + basePath);
        logger.info("path:" + path);
        logger.info("userkey:"+request.getSession().getAttribute("userkey"));
        if(request.getSession().getAttribute("userkey") == null){

            logger.info("尚未登录，跳转到登录界面");

            return false;
        }
        logger.info("已登录，放行！");
        return true;*/


    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 Exception ex) throws Exception {
    }
}
