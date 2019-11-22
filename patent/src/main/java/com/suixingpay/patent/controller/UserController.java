package com.suixingpay.patent.controller;

import com.alibaba.fastjson.JSON;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 登陆页面 （包括用户登录 管理员登录）
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param httpSession
     * @return
     */
    @RequestMapping("/login")
    public String userLogin(String userAccount, String userPassword, HttpSession httpSession) {
        Message message = userService.selectUserByUserAccountAndUserPassword(userAccount, userPassword);
        return JSON.toJSONString(message);
    }

    //管理员方法

    /**
     * 管理员增加用户
     *
     * @param user 新用户
     * @return
     */
    @RequestMapping("/admInsert")
    public Map<String, Object> insertUser(User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            userService.insertUser(user);
            map.put("insert", "新增用户成功");
            return map;
        }
        catch (Exception e) {
            map.put("insert", "新增用户失败");
            return map;
        }
    }

    /**
     * 管理员逻辑删除，修改用户的状态为0，代表用户失效
     *
     * @param userId 用户id
     * @return return "删除用户成功";
     */
    @RequestMapping("/admDelete")
    public Map<String, Object> updateUser(int userId) {
        Map<String, Object> map = new HashMap<>();
        try {
            userService.updateUserByUserId(userId);
            map.put("delete", "删除用户成功");
            return map;
        }
        catch (Exception e) {
            map.put("delete", "删除用户失败");
            return map;
        }
    }


    /**
     * 管理员展示所有用户
     *
     * @return 全部用户列表
     */
    @RequestMapping("/admSelectAll")
    public Map<String, Object> selectAllUser() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<User> userList = userService.selectAllUser();
            map.put("allUser", userList);
            return map;
        }
        catch (Exception e) {
            map.put("allUser", "展示所有用户失败");
            return map;
        }
    }

    /**
     * 管理员通过用户id展示用户
     *
     * @param userId 用户id
     * @return
     */
    @RequestMapping("/admSelectOne")
    public Map<String, Object> selectUserByUserId(int userId) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = userService.selectUserByUserId(userId);
            map.put("user", user);
            return map;
        }
        catch (Exception e) {
            map.put("user", "展示用户失败");
            return map;
        }
    }

    //用户方法

    /**
     * 用户显示自己个人信息
     *
     * @param httpSession
     * @return
     */
    @RequestMapping("/userSelect")
    public Map<String, Object> selectMyInformation(HttpSession httpSession) {
        Map<String, Object> map = new HashMap<>();
        User userLogin = (User) httpSession.getAttribute("user");
        Integer userId = userLogin.getUserId();
        User user = userService.selectUserByUserId(userId);
        map.put("myInfo", user);
        return map;
    }

    /**
     * 用户修改自己的名字和密码
     *
     * @param user
     * @return
     */
    @RequestMapping("/userUpdate")
    public Map<String, Object> userUpdatePassword(User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            userService.updateLocalUserByUserId(user.getUserId(), user.getUserName(), user.getUserPassword());
            map.put("update", "修改成功");
            return map;
        }
        catch (Exception e) {
            map.put("update", "修改失败");
            return map;
        }
    }
}
