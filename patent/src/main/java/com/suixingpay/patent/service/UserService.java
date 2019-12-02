package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    /**
     * 登陆页面 （包括用户登录 管理员登录）
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param httpSession
     * @return
     */
    ResponseEntity<Message> selectUserByUserAccountAndUserPassword(String userAccount, String userPassword,
                                                                   HttpServletRequest request);

    /**
     * 管理员增加用户
     *
     * @param user 新用户
     * @return
     */
    ResponseEntity<Message> insertUser(User user);

    /**
     * 管理员逻辑删除，修改用户的状态为0，代表用户失效
     *
     * @param userId 用户id
     * @return return "删除用户成功";
     */
    ResponseEntity<Message> updateUserByUserId(int userId);

    /**
     * 管理员展示所有用户
     *
     * @return 全部用户列表
     */
    ResponseEntity<Message> selectAllUser();

    /**
     * 管理员通过用户id展示用户
     *
     * @param userId 用户id
     * @return
     */
    ResponseEntity<Message> selectUserByUserId(int userId);

    /**
     * 用户修改自己的名字和密码
     * 先跳转一下展示个人信息在跳转修改
     *
     * @return
     */
    ResponseEntity<Message> updateLocalUserByUserId(int userId, String userName, String userPassword);

    /**
     * 管理员模糊查找
     *
     * @param user
     * @return
     */
    ResponseEntity<Message> findUser(User user);

    /**
     * 通过用户账号查找用户
     * @param userAccount 用户账号
     * @return
     */
    User selectUserByUserAccount(String userAccount);

    /**
     *管理员解封账号
     * @param userId 用户id
     * @return
     */
    ResponseEntity<Message> removeUserByUserId(Integer userId);
}
