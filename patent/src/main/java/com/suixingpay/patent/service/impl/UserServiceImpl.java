package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.UserMapper;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    Message message = new Message();

    @Override
    public ResponseEntity<Message> selectUserByUserAccountAndUserPassword(String userAccount, String userPassword,
                                                                          HttpServletRequest request) {
        Message message = new Message();
        String password = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        User user = userMapper.selectUserByUserAccountAndUserPassword(userAccount, password);
        if (user == null) {
            message.setMessage(null, 400, "账号或密码不正确", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        else if (user.getUserStatus() == 0) {
            message.setMessage(null, 400, "用户状态失效", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        else if (user.getUserId() == 1) {
            message.setMessage(user, 200, "管理员登录成功", true);
            request.getSession().setAttribute("user", user);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        else {
            message.setMessage(user, 200, "用户登录成功", true);
            request.getSession().setAttribute("user", user);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Message> insertUser(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss ");//
        Date date = new Date();// 获取当前时间
        user.setUserCreateTime(date);
        user.setUserStatus(1);
        user.setUserPassword(DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes()));
        int changeLine = userMapper.insertUser(user);
        if (changeLine == 0) {
            message.setMessage(null, 400, "新增用户失败", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        message.setMessage(null, 200, "新增用户成功", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Message> updateUserByUserId(int userId) {
        int changeLine = userMapper.updateUserByUserId(userId);
        if (changeLine == 0) {
            message.setMessage(null, 400, "删除用户失败", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        message.setMessage(null, 200, "删除用户成功", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Message> selectAllUser() {
        List<User> userList= userMapper.selectAllUser();
        if (userList.size()==0){
            message.setMessage(null,200,"暂无用户，请添加用户~",true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(userList,200,"查询用户成功",true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Message> selectUserByUserId(int userId) {
        User user= userMapper.selectUserByUserId(userId);
        if (user==null){
            message.setMessage(null,200,"无此用户",true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(user,200,"查询所有用户成功",true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Message> updateLocalUserByUserId(int userId, String userName, String userPassword) {
        int changeLine= userMapper.updateLocalUserByUserId(userId, userName, userPassword);
        if (changeLine == 0) {
            message.setMessage(null, 400, "修改用户失败", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        message.setMessage(null, 200, "修改用户成功", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Message> findUser(User user) {
        List<User> userList= userMapper.findUser(user);
        if (userList.size()==0){
            message.setMessage(null,200,"暂无符合筛选条件的用户~",true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(userList,200,"查询用户成功",true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @Override
    public User selectUserByUserAccount(String userAccount) {
        return userMapper.selectUserByUserAccount(userAccount);
    }

}
