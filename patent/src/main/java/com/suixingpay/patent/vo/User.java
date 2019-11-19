package com.suixingpay.patent.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Date;


@Getter
@Setter
@ToString
@Component
public class User {
    private int userId;//用户id
    private String userName;//用户名
    private String userAccount;//用户账号
    private String userPassword;//用户密码
    private int userStatus;//用户状态
    private Date userCreateTime;//用户创建时间
}