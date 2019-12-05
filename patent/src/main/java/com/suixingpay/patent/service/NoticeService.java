package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Notice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@Service
public interface NoticeService {
    /**
     * 交底书上传
     * @param patentId
     * @param fils
     * @param request
     * @return
     */
    ResponseEntity<Message> insert(Integer patentId, MultipartFile[] fils, HttpServletRequest request);



    /**
     * 交底书下载
     * @param noticeId
     * @return
     */
    Notice selectNoticeByPatentId(int noticeId);



    /**
     * 交底书进行逻辑删除
     * @param noticeId
     * @return
     */
    ResponseEntity<Message> delete(int noticeId);



    /**
     * 用户查询
     * @param noticePatentId
     * @return
     */
    ResponseEntity<Message> selectByPatentId(int noticePatentId);



    /**
     * 管理员查询
     * @param noticePatenId
     * @return
     */
    ResponseEntity<Message> searchmanagerId(int noticePatenId);



}
