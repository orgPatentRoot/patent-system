package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
//    int insert(User record);
//
//    int insertSelective(User record);

    User selectUserByUserAccountAndUserPassword(@Param("userAccount") String userAccount,@Param("userPassword")  String userPassword);

    void insertUser(User user);

    void updateUserByUserId(int userId);

    List<User> selectAllUser();

    User selectUserByUserId(int userId);

    void updateLocalUserByUserId(int userId, String userName, String userPassword);
}