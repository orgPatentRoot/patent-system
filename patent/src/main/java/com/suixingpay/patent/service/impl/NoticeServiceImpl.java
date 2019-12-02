package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.NoticeMapper;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Notice;
import com.suixingpay.patent.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    private Message message = new Message();

    //下载
    @Override
    public Notice selectNoticeByPatentId(int noticeId) {
        return noticeMapper.selectNoticeByPatentId(noticeId);
    }

    //上传
    @Override
    public int insert(Notice record) {
        return noticeMapper.insert(record);
    }


    //逻辑删除
    @Override
    public ResponseEntity<Message> delete(int noticeId) {
        int changeLine = noticeMapper.delete(noticeId);
        if (changeLine == 0) {
            message.setMessage(null, 400, "删除交底书失败", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        message.setMessage(null, 200, "删除交底书成功", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }


    //用户查看
    @Override
    public ResponseEntity<Message> selectByPatentId(int noticePatentId) {
        ArrayList<Object> arrayList = new ArrayList<>();
        List<Notice> noticeList = noticeMapper.selectByPatentId(noticePatentId);
        if (noticeList.size() == 0) {
            message.setMessage(arrayList, 200, "暂无交底书", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        for (Notice notice : noticeList) {
            if (notice.getNoticeStatus() == 1) {
                arrayList.add(notice);
            }
        }
        message.setMessage(arrayList, 200, "交底书展示", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }


    //管理员查看
    @Override
    public ResponseEntity<Message> searchmanagerId(int noticePatenId) {
        List<Notice> noticeList = noticeMapper.selectByPatentId(noticePatenId);
        if (noticeList.size() == 0) {
            message.setMessage(noticeList, 200, "暂无交底书", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(noticeList, 200, "交底书展示", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }


}
