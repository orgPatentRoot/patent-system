package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Notice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public interface NoticeService {
    //下载
    Notice selectNoticeByPatentId(int noticeId);
    //上传
    int insert(Notice record);
    //逻辑删除
    ResponseEntity<Message> delete(int noticeId);
    //用户查询
    ResponseEntity<Message> selectByPatentId(int noticePatentId);
    //管理员查询
    ResponseEntity<Message> searchmanagerId(int noticePatenId);


}
