package com.suixingpay.patent.mapper;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer noticeId);

    int insert(com.suixingpay.patent.pojo.Notice record);

    int insertSelective(com.suixingpay.patent.pojo.Notice record);

    com.suixingpay.patent.pojo.Notice selectByPrimaryKey(Integer noticeId);

    int updateByPrimaryKeySelective(com.suixingpay.patent.pojo.Notice record);

    int updateByPrimaryKey(com.suixingpay.patent.pojo.Notice record);
}