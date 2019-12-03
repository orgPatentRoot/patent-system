package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.Patent;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface PatentMapper {
    int insertPatent(Patent patent); //插入专利

    int updatePatent(Patent patent); //修改专利信息

    List<Patent> selectPatent(Patent patent); //查看专利

    List<Patent> selectPatentWithIndex(Patent patent); //指标维度查询
}