package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PatentMapper {
    int insert(Patent record);
}