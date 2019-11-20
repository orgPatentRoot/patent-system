package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.Patent;

public interface PatentMapper {
    int deleteByPrimaryKey(Integer patentId);

    int insert(Patent record);

    int insertSelective(Patent record);

    Patent selectByPrimaryKey(Integer patentId);

    int updateByPrimaryKeySelective(Patent record);

    int updateByPrimaryKeyWithBLOBs(Patent record);

    int updateByPrimaryKey(Patent record);
}