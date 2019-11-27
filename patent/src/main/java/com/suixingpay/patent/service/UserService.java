package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.User;

import javax.servlet.http.HttpSession;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<Message> selectUserByUserAccountAndUserPassword(String userAccount, String userPassword, HttpSession httpSession);

    ResponseEntity<Message> insertUser(User user);

    ResponseEntity<Message> updateUserByUserId(int userId);

    ResponseEntity<Message> selectAllUser();

    ResponseEntity<Message> selectUserByUserId(int userId);

    ResponseEntity<Message> updateLocalUserByUserId(int userId, String userName, String userPassword);

    ResponseEntity<Message> findUser(User user);

    User selectUserByUserAccount(String userAccount);
}
