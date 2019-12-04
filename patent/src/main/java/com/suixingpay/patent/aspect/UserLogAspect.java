package com.suixingpay.patent.aspect;


import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/*@Aspect
@Component
public class UserLogAspect {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private HistoryService historyService;

    @Autowired
    private History history;

    @Pointcut("@annotation(com.suixingpay.patent.annotation.UserLog)")
    public void logPointCut() {
    }

    @After("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        User user = (User) session.getAttribute("user");
        //获取专利ID
        String patentIds = attributes.getRequest().getParameter("patentId");

        if(patentIds == null) {
            patentIds = session.getAttribute("patentId") == null ? null : (String) session.getAttribute("patentId");
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取UserLog注解
        UserLog userLog = method.getAnnotation(UserLog.class);
        //获取注解中的value值
        String modification = userLog.value();
        try {
            int patentId = Integer.valueOf(patentIds);
            //从切面织入点处通过反射机制获取织入点处的方法

            //创建history对象
            history.setHistoryModification(modification);
            history.setHistoryPatent(patentId);
            history.setHistoryUser(user.getUserName());
            history.setHistoryCreateTime(new Date());

            historyService.insertHistory(history);

        }catch (Exception e){
            e.getMessage();
        }
    }

}*/
