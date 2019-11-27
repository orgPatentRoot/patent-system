package com.suixingpay.patent.controller;

import com.suixingpay.patent.annotation.UserLog;
import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @RequestMapping("test")
    @UserLog(value = "测试")
    public String test(@RequestBody Patent patent, HttpSession httpSession){
        User user = new User();
        user.setUserName("zhangsan");
        httpSession.setAttribute("user",user);
        return "test";
    }
}
