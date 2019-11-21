package com.suixingpay.patent.controller;

import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.suixingpay.patent.util.ParamCheck.paramCheck;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 登陆页面 （包括用户登录 管理员登录）
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param httpSession
     * @return
     */
    @RequestMapping("/login")
    public String userLogin(String userAccount, String userPassword, HttpSession httpSession) {
        boolean flag = paramCheck(userAccount, userPassword);
        if (flag) {
            User userLogin = userService.selectUserByUserAccountAndUserPassword(userAccount, userPassword);
            if (userLogin == null) {
                return "用户不存在";
            }
            if (userLogin.getUserStatus()==0){
                return "用户状态失效无效";
            }
            if (userLogin.getUserId()==1){
                return "管理员页面";
            }
            else {
                httpSession.setAttribute("user",userLogin);
                return "登录成功 用户页面";
            }
        }
        return "不能输入空数据";
    }

    //管理员方法

    /**
     * 管理员增加用户
     * @param user 新用户
     * @return
     */
    @RequestMapping("/admInsert")
    public String insertUser(User user){
        userService.insertUser(user);
        return "新增用户成功";
    }

    /**
     * 管理员逻辑删除，修改用户的状态为0，代表用户失效
     * @param userId 用户id
     * @return
     */
    @RequestMapping("/admDelete")
    public String updateUser(int userId){
        userService.updateUserByUserId(userId);
        return "删除用户成功";
    }

    /**
     * 管理员展示所有用户
     * @return 全部用户列表
     */
    @RequestMapping("/admSelectAll")
    public List<User> selectAllUser(){
        List<User> userList=userService.selectAllUser();
        return userList;
    }

    /**
     * 管理员通过用户id展示用户
     * @param userId 用户id
     * @return
     */
    @RequestMapping("/admSelectOne")
    public User selectUserByUserId(int userId){
        User user=userService.selectUserByUserId(userId);
        return user;
    }

    //用户方法

    /**
     * 用户显示自己个人信息
     * @param httpSession
     * @return
     */
    @RequestMapping("/userSelect")
    public User selectMyInformation(HttpSession httpSession){
        User userLogin = (User) httpSession.getAttribute("user");
        Integer userId=userLogin.getUserId();
        User user=userService.selectUserByUserId(userId);
        return user;
    }

    /**
     * 用户修改自己的名字和密码
     * @param user
     * @return
     */
    @RequestMapping("/userUpdate")
    public String userUpdatePassword(User user){
        userService.updateLocalUserByUserId(user.getUserId(),user.getUserName(),user.getUserPassword());
        return "修改成功";
    }



}
