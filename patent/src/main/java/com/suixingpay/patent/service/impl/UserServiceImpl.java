package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.UserMapper;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Message selectUserByUserAccountAndUserPassword(String userAccount, String userPassword,HttpSession httpSession) {
        Message message = new Message();
        User user = userMapper.selectUserByUserAccountAndUserPassword(userAccount, userPassword);
         httpSession.setAttribute("user",user);
        if (user == null) {
            //状态2代表账号或密码不正确
            message.setState(-1);
            message.setFlag(false);
            message.setObject(null);
            return message;
        }else if (user.getUserStatus() == 0) {
            //状态0代表用户状态失效
            message.setObject(null);
            message.setFlag(false);
            message.setState(0);
            return message;
        }else if (user.getUserId() == 1) {
            //状态为1代表管理员登陆
            message.setFlag(true);
            message.setState(1);
            message.setObject(user);
            return message;
        }
        //普通用户登录
        else {
            message.setFlag(true);
            message.setObject(user);
            message.setState(2);
            return message;
        }
    }

    @Override
    public void insertUser(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss ");//
        Date date = new Date();// 获取当前时间
        user.setUserCreateTime(date);
        user.setUserStatus(1);
        userMapper.insertUser(user);
    }

    @Override
    public void updateUserByUserId(int userId) {
        userMapper.updateUserByUserId(userId);
    }

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public User selectUserByUserId(int userId) {
        return userMapper.selectUserByUserId(userId);
    }

    @Override
    public void updateLocalUserByUserId(int userId, String userName, String userPassword) {
        userMapper.updateLocalUserByUserId(userId, userName, userPassword);
    }

}
