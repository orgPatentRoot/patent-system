package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NoticeMapper {

    //上传
    int insert(Notice record);

    //逻辑删除
    int delete(int noticeId);

    //下载
    Notice selectNoticeByPatentId(int noticeId);

    //用户以及管理员查看
    List<Notice> selectByPatentId(int noticePatentId);


}