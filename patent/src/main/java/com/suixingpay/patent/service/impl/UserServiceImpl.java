package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.UserMapper;
import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User selectUserByUserAccountAndUserPassword(String userAccount, String userPassword) {
        return userMapper.selectUserByUserAccountAndUserPassword(userAccount, userPassword);
    }

    @Override
    public void insertUser(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss ");//
        Date date = new Date();// 获取当前时间
        user.setUserCreateTime(date);
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
        userMapper.updateLocalUserByUserId(userId,userName,userPassword);
    }

}
