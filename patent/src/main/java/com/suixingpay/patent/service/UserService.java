package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    Message selectUserByUserAccountAndUserPassword(String userAccount, String userPassword, HttpSession httpSession);

    void insertUser(User user);

    void updateUserByUserId(int userId);

    List<User> selectAllUser();

    User selectUserByUserId(int userId);

    void updateLocalUserByUserId(int userId,String userName,String userPassword);
}
