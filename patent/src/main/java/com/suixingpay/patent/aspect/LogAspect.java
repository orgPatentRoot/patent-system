package com.suixingpay.patent.aspect;

import com.suixingpay.patent.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class LogAspect {

    @Pointcut("execution(* com.suixingpay.patent.controller.*.*(..))&&!execution(* com.suixingpay.patent.controller.UserController.userLogin(..))")
    public void pointcut() {
    }

    @After("pointcut()")
    public void afterMethod(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取用户名
        //String userName = (String) request.getSession(true).getAttribute("userName");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        //获取专利名
        String patentCaseNum = request.getParameter("patentCaseNum");

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取请求的方法名
        String methodName = method.getName();

        log.info("用户{}对专利{}进行了{}",user.getUserName(),patentCaseNum,methodName);
    }
}
