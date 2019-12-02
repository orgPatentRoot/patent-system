package com.suixingpay.patent.controller;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.suixingpay.patent.util.ParamCheck.paramCheck;


@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=utf-8")
public class UserController {
    @Autowired
    private UserService userService;

    private Message message = new Message();

    @GetMapping("/message")
    public String login(){
        return "当前尚未登录！请登录后重试！";
    }

    /**
     * 登陆页面 （包括用户登录 管理员登录）
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Message> userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (!paramCheck(userAccount, userPassword)) {
            message.setMessage(null, 400, "不能输入空数据", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return userService.selectUserByUserAccountAndUserPassword(userAccount, userPassword, request);
    }

    //管理员方法

    /**
     * 管理员增加用户
     *
     * @param user 新用户
     * @return
     */
    @PostMapping("/admInsert")
    public ResponseEntity<Message> insertUser(@RequestBody User user) {
        User exitUser = userService.selectUserByUserAccount(user.getUserAccount());
        if (exitUser != null) {
            message.setMessage(null, 400, "用户已存在", false);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }

        if (!paramCheck(user.getUserName(), user.getUserAccount(), user.getUserPassword())) {
            message.setMessage(null, 400, "不能输入空数据", false);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        return userService.insertUser(user);

    }

    /**
     * 管理员逻辑删除，修改用户的状态为0，代表用户失效
     *
     * @param userId 用户id
     * @return return "删除用户成功";
     */
    @GetMapping("/admDelete")
    public ResponseEntity<Message> updateUser(Integer userId) {
        if (userId == null || "".equals(userId)) {
            message.setMessage(null, 400, "不能输入空数据", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return userService.updateUserByUserId(userId);
    }

    /**
     * 管理员解封账号
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping("/admRemoveUser")
    public ResponseEntity<Message> removeUser(Integer userId) {
        if (userId == null || "".equals(userId)) {
            message.setMessage(null, 400, "不能输入空数据", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return userService.removeUserByUserId(userId);
    }

    /**
     * 管理员展示所有用户
     *
     * @return 全部用户列表
     */
    @GetMapping("/admSelectAll")
    public ResponseEntity<Message> selectAllUser() {
        return userService.selectAllUser();
    }

    /**
     * 管理员通过用户id展示用户
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping("/admSelectOne")
    public ResponseEntity<Message> selectUserByUserId(Integer userId) {
        if (userId == null || "".equals(userId)) {
            message.setMessage(null, 400, "不能输入空数据", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return userService.selectUserByUserId(userId);
    }

    //用户方法

    /**
     * 用户显示自己个人信息
     *
     * @param httpSession
     * @return
     */
    @GetMapping("/userSelect")
    public ResponseEntity<Message> selectMyInformation(HttpSession httpSession) {
        User userLogin = (User) httpSession.getAttribute("user");
        if (userLogin.getUserId() == null || "".equals(userLogin.getUserId())) {
            message.setMessage(null, 400, "请先登录", false);
        }
        return userService.selectUserByUserId(userLogin.getUserId());
    }

    /**
     * 用户修改自己的名字和密码
     * 先跳转一下展示个人信息在跳转修改
     *
     * @param user
     * @return
     */
    @PostMapping("/userUpdate")
    public ResponseEntity<Message> userUpdatePassword(@RequestBody User user) {
        if (!paramCheck(user.getUserName(), user.getUserPassword())) {
            message.setMessage(null, 400, "不能输入空数据", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return userService.updateLocalUserByUserId(user.getUserId(), user.getUserName(), user.getUserPassword());
    }


    /**
     * 管理员模糊查找
     *
     * @param user
     * @return
     */
    @PostMapping("/find")
    public ResponseEntity<Message> findUser(@RequestBody User user) {
        return userService.findUser(user);
    }
}
