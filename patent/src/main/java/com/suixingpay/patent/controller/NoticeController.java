package com.suixingpay.patent.controller;


import com.suixingpay.patent.annotation.UserLog;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.suixingpay.patent.pojo.Notice;

@RestController
@RequestMapping("/file")
@Slf4j
public class NoticeController {
    //使用指定类初始化日志对象,在日志输出的时候，可以打印出日志信息所在类
    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);
    /**
     * 文件上传
     * 要一个文件以及文件的专利id
     */
    @Autowired
    private NoticeService noticeService;
    private Message message = new Message();

    @Value("${visualpath}")
    private String visualPath;

    @UserLog("上传交底书！")
    @RequestMapping("/upload")
    @ResponseBody
    public ResponseEntity<Message> upload(@RequestParam("patentId") Integer patentId,
                                          @RequestParam("file") MultipartFile[] fils, HttpServletRequest request) {
        for(MultipartFile file:fils){
            if (patentId == null || file == null) {
                message.setMessage(null, 400,   "没有文件ID或者文件上传", false);
                return new ResponseEntity<Message>(message, HttpStatus.OK);
            }

        if  (file.isEmpty()) {
                message.setMessage(null, 400, "没有选择上传文件", false);
                return new ResponseEntity<Message>(message, HttpStatus.OK);
            }
            if  (file.getSize() > 1024 * 1024 * 5) {
                message.setMessage(null, 400, "文档不能超过5M", false);
                return new ResponseEntity<Message>(message, HttpStatus.OK);
            }


            //获取文件的专利Id
            int filePaentId = patentId;
            //获取文件的名字
            String fileName = file.getOriginalFilename();
            //文件类型仅限txt、doc、docx、ppt、pdf、rar、zip、xls、xlsx、png、jpg类型
            if(fileName.endsWith(".txt") || fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".ppt")
                    || fileName.endsWith(".rar") || fileName.endsWith(".zip") || fileName.endsWith(".xls")|| fileName.endsWith(".xlsx")
                    || fileName.endsWith(".xls") || fileName.endsWith(".xlsx") || fileName.endsWith(".png") || fileName.endsWith(".jpg")
                    || fileName.endsWith(".pdf")) {
                System.out.println(fileName);
                String fileName1 = fileName.substring(fileName.lastIndexOf("."));
                Date date = new Date();

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

                String str  =  format.format(date);
                fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "-" + str + fileName1;

                //        //把上传的文件拼接并且放在项目的路径下
                String projectUrl = request.getSession().getServletContext().getRealPath("/");
                System.out.println(projectUrl);
                String path = projectUrl  + fileName;
                System.out.println(path);
                String url = visualPath + fileName;
                System.out.println(url);
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
                    noticeService.insert(files);

                } catch (IOException e) {
                    LOGGER.error(e.toString(), e);
                    message.setMessage(null, 400, "文件上传失败", false);
                    return new ResponseEntity<Message>(message, HttpStatus.OK);
                }
            }else {
                message.setMessage(null, 400, "文件类型仅限txt、doc、docx、ppt、pdf、rar、zip、xls、xlsx、png、jpg类型！", false);
                return new ResponseEntity<Message>(message, HttpStatus.OK);
            }
        }
        message.setMessage(null, 200, "上传成功", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);

    }

    /**
     *
     * @param noticeId  文件的id主键自增长
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/download")
    public ResponseEntity<Message>  downloadFile(@Param("noticeId") Integer noticeId,
            HttpServletResponse response) throws UnsupportedEncodingException, UnknownHostException {
        if (noticeId == null) {
            message.setMessage(null, 400,   "没有需要下载文件ID", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        Notice notice = noticeService.selectNoticeByPatentId(noticeId);
        if (notice == null) {
            message.setMessage(null, 400, "文件ID不存在", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        //获取文件地址
        String noticePath = notice.getNoticePath();
        message.setMessage(noticePath, 200, "文件下载成功", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }


    /**
     * 文件进行逻辑删除，修改数据库的状态
     *
     * @param noticeId 文件的id主键自增长
     * @return
     */
    @UserLog("删除交底书！")
    @RequestMapping("/delete")
    public ResponseEntity<Message> delete(Integer noticeId) {
        if (noticeId == null) {
            message.setMessage(null, 400,   "没有文件ID", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return noticeService.delete(noticeId);
    }





    /**
     * 用户查看
     * @param noticePatentId
     * @return
     */
    @RequestMapping("/select")
    public ResponseEntity<Message>  selectByPatentId(Integer noticePatentId) {
        if (noticePatentId == null) {
            message.setMessage(null, 400,   "没有文件ID", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        System.out.println(noticePatentId);
        return  noticeService.selectByPatentId(noticePatentId);
    }
    /**
     * 管理员查看
     * @param noticePatentId
     * @return
     */
    @RequestMapping("/searchManager")
    public ResponseEntity<Message>  searchmanagerId(Integer noticePatentId) {
        if (noticePatentId == null) {
            message.setMessage(null, 400,   "没有文件ID", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return  noticeService.searchmanagerId(noticePatentId);

    }


}





