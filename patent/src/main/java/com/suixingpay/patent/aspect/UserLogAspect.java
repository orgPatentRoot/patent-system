package com.suixingpay.patent.aspect;


import com.suixingpay.patent.annotation.UserLog;
import com.suixingpay.patent.pojo.History;
import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.service.HistoryService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class UserLogAspect {
    @Autowired
    HistoryService historyService;

    @Autowired
    History history;

    @Pointcut("@annotation(com.suixingpay.patent.annotation.UserLog)")
    public void logPointCut() {
    }

    @After("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取用户名
        //String userName = (String) request.getSession(true).getAttribute("userName");
        HttpSession session = request.getSession();
        //获取用户
        User user = (User)session.getAttribute("user");

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //获取专利名
        String patentIds = request.getParameter("patentId");
        int patentId = Integer.valueOf(patentIds);
        if(patentIds==null){
            //获取前台传的参数名
            String[] parameterNames = signature.getParameterNames();
            //获取前台传的值
            String[] parameterValues = (String[])joinPoint.getArgs();
            for (int i=0;i<parameterValues.length;i++) {
                if(parameterNames[i]=="patentId"){
                    patentId = Integer.valueOf(parameterValues[i]);
                }
            }
        }


        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取UserLog注解
        UserLog userLog = method.getAnnotation(UserLog.class);
        //获取注解中的value值
        String modification = userLog.value();



        //创建history对象
        history.setHistoryModification(modification);
        history.setHistoryPatent(patentId);
        history.setHistoryUser(user.getUserName());
        history.setHistoryCreateTime(new Date());

        historyService.insertHistory(history);


    }

}
