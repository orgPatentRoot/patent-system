package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.controller.NoticeController;
import com.suixingpay.patent.mapper.NoticeMapper;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Notice;
import com.suixingpay.patent.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {
    //使用指定类初始化日志对象,在日志输出的时候，可以打印出日志信息所在类
    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);
    @Autowired
    private NoticeMapper noticeMapper;
    private Message message = new Message();


    //获得配置文件ip路径
    @Value("${visualpath}")
    private String visualPath;

    /**
     * 交底书上传
     * @param patentId 交底书专利ID
     * @param fils      交底书可以上传多个
     * @param request   request请求
     * @return
     */
    @Override
    public ResponseEntity<Message> insert(Integer patentId, MultipartFile[] fils, HttpServletRequest request) {
        //文件参数安全判断
        if (fils.length == 0) {
            message.setMessage(null, 200,   "没有文件上传", false);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        //遍历多文件上传
        for (MultipartFile file : fils) {
            //判断是否为空
            if (patentId == null || file == null || file.isEmpty()) {
                message.setMessage(null, 200,   "没有文件ID或者没有文件上传", false);
                return new ResponseEntity<Message>(message, HttpStatus.OK);
            }
//            //判断文件是否为空
//            if  (file.isEmpty()) {
//                message.setMessage(null, 200, "没有选择上传文件", false);
//                return new ResponseEntity<Message>(message, HttpStatus.OK);
//            }
            //判断文件大小
            if  (file.getSize() > 1024 * 1024 * 5) {
                message.setMessage(null, 200, "文档不能超过5M", false);
                return new ResponseEntity<Message>(message, HttpStatus.OK);
            }

            //获取文件的名字
            String fileName = file.getOriginalFilename();
            //文件类型仅限txt、doc、docx、ppt、pdf、rar、zip、xls、xlsx、png、jpg类型
            if (!(fileName.endsWith(".txt") || fileName.endsWith(".doc") || fileName.endsWith(".docx")
                    || fileName.endsWith(".ppt") || fileName.endsWith(".rar") || fileName.endsWith(".zip")
                    || fileName.endsWith(".xls") || fileName.endsWith(".xlsx") || fileName.endsWith(".xls")
                    || fileName.endsWith(".xlsx") || fileName.endsWith(".png") || fileName.endsWith(".jpg")
                    || fileName.endsWith(".pdf"))) {
                message.setMessage(null, 200, "文件类型仅限txt、doc、docx、ppt、pdf、rar、zip、xls、xlsx、png、jpg类型！", false);
                return new ResponseEntity<Message>(message, HttpStatus.OK);
            }
        }
        for (MultipartFile file : fils) {
            //获取文件的专利Id
            int filePaentId = patentId;
            //获取文件的名字
            String fileName = file.getOriginalFilename();
            //截取文件的后缀
            String fileName1 = fileName.substring(fileName.lastIndexOf("."));
            //获取当前时间
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String str  =  format.format(date);

            //把文件名的前缀拼上当前时间以及文件后缀
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "-" + str + fileName1;

            //把上传的文件拼接并且放在项目的路径下找到指定的public
            String projectUrl = request.getSession().getServletContext().getRealPath("/");
            //获取文件的路径以及当前文件
            String path = projectUrl  + fileName;
            //转成网络传输http加上文件的名字
            String url = visualPath + fileName;

            File dest = new File(path);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            //开始上传到项目路径下，并且上传到数据库
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
                out.write(file.getBytes());
                out.flush();
                out.close();
                //成功后打印到控制台
                LOGGER.info("上传成功");
                //上传到数据库
                Notice files = new Notice();
                files.setNoticeCreateTime(new Date());
                files.setNoticeName(file.getOriginalFilename());
                files.setNoticePatentId(filePaentId);
                files.setNoticePath(url);
                files.setNoticeStatus(1);
                noticeMapper.insert(files);

            } catch (IOException e) {
                LOGGER.error(e.toString(), e);
                //当文件上传失败的时候进行回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                message.setMessage(null, 200, "上传失败", true);
                return new ResponseEntity<Message>(message, HttpStatus.OK);
            }
        }
        message.setMessage(null, 200, "上传成功", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }




    /**
     * 交底书下载
     * @param noticeId 交底书ID
     * @return
     */
    @Override
    public Notice selectNoticeByPatentId(int noticeId) {
        return noticeMapper.selectNoticeByPatentId(noticeId);
    }



    /**
     * 交底书进行逻辑删除
     * @param noticeId
     * @return
     */
    @Override
    public ResponseEntity<Message> delete(int noticeId) {
        //影响的行数
        int changeLine = noticeMapper.delete(noticeId);
        //如果影响的行数等于0
        if (changeLine == 0) {
            message.setMessage(null, 400, "删除交底书失败", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        message.setMessage(null, 200, "删除交底书成功", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }



    /**
     * 用户查看
     * @param noticePatentId
     * @return
     */
    @Override
    public ResponseEntity<Message> selectByPatentId(int noticePatentId) {

        ArrayList<Object> arrayList = new ArrayList<>();
        //根据ID去查数据库，返回到集合
        List<Notice> noticeList = noticeMapper.selectByPatentId(noticePatentId);
        //判断集合的长度是否为0
        if (noticeList.size() == 0) {
            message.setMessage(arrayList, 200, "暂无交底书", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        //遍历这个集合
        for (Notice notice : noticeList) {
            //判断交底书的状态是否为1
            if (notice.getNoticeStatus() == 1) {
                arrayList.add(notice);
            }
        }
        message.setMessage(arrayList, 200, "交底书展示", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }


    /**
     * 管理员查看
     * @param noticePatenId
     * @return
     */
    @Override
    public ResponseEntity<Message> searchmanagerId(int noticePatenId) {
        //根据ID去查数据库，返回到集合
        List<Notice> noticeList = noticeMapper.selectByPatentId(noticePatenId);
        //判断交底书是否为1
        if (noticeList.size() == 0) {
            message.setMessage(noticeList, 200, "暂无交底书", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        //存在交底书就展示
        message.setMessage(noticeList, 200, "交底书展示", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }




}
