package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.Patent;

import java.util.List;

public interface PatentMapper {
    int insertPatent(Patent patent);//插入专利

    int updatePatent(Patent patent);//通过专利ID和（创建人或撰写人）修改专利信息

    int updatePatentWriterByID(Patent patent);//通过专利Id修改专利撰写人信息

    int updatePatentDudit(Patent patent);//修改专利审核状态

    int updateStatusId(Patent patent);//修改专利进度状态

    List<Patent> selectPatent(Patent patent);//查看专利

    Patent selectPatentCount(Patent patent);//查看专利总数

}