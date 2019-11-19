package com.suixingpay.patent.dao;

import com.suixingpay.patent.vo.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PatentDao {
    @Select("SELECT * FROM patent")
    public Patent findAll();
}
