package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {


    User selectUserByUserAccountAndUserPassword(@Param("userAccount") String userAccount, @Param("userPassword") String userPassword);

    int insertUser(User user);

    int updateUserByUserId(int userId);

    List<User> selectAllUser();

    User selectUserByUserId(int userId);

    int updateLocalUserByUserId(int userId, String userName, String userPassword);

    List<User> findUser(User user);

    User selectUserByUserAccount(String userAccount);
}