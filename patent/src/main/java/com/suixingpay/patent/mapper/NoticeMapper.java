package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NoticeMapper {

    /**
     * 文件上传
     * @param record
     * @return
     */
    int insert(Notice record);

    /**
     * 逻辑删除
     * @param noticeId
     * @return
     */
    int delete(int noticeId);

    /**
     * 文件下载
     * @param noticeId
     * @return
     */
    Notice selectNoticeByPatentId(int noticeId);

    /**
     * 用户以及管理员查看
     * @param noticePatentId
     * @return
     */
    List<Notice> selectByPatentId(int noticePatentId);


}