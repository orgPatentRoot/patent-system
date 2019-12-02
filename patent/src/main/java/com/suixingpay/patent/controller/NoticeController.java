package com.suixingpay.patent.controller;


import com.suixingpay.patent.annotation.UserLog;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;
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


//    @UserLog("上传文件")
    @RequestMapping("/upload")
    @ResponseBody

    public ResponseEntity<Message> upload(@RequestParam("patentId") int patentId,
                                          @RequestParam("file") MultipartFile file) {
        System.out.println("进来");
        if  (file.isEmpty()) {
            message.setMessage(null, 400, "没有选择上传文件", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        if  (file.getSize() > 1024 * 1024 * 5) {
            message.setMessage(null, 400, "文档不能超过5M", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }

        //获取文件的专利Id
        int filePaentId = patentId;
        //获取文件的名字
        String fileName = file.getOriginalFilename();
        //获取当前项目根路径
        File rootPath = new File("");
        String filePath = "";
        try {
            filePath = rootPath.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把上传的文件拼接并且放在项目的路径下
        filePath = filePath  +  "/patent/src/main/resources/notice/" + UUID.randomUUID() + fileName;
        File dest = new File(filePath);

        //开始上传到项目路径下，并且上传到数据库
        try {
            file.transferTo(dest);
            //成功后打印到控制台
            LOGGER.info("上传成功");
            //上传到数据库
            Notice files = new Notice();
            files.setNoticeCreateTime(new Date());
            files.setNoticeName(fileName);
            files.setNoticePatentId(filePaentId);
            files.setNoticePath(filePath);
            files.setNoticeStatus(1);
            noticeService.insert(files);
            message.setMessage(null, 200, "上传成功", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        message.setMessage(null, 400, "文件上传失败", false);
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 文件进行逻辑删除，修改数据库的状态
     *
     * @param noticeId 文件的id主键自增长
     * @return
     */
    @RequestMapping("/delete")
    public ResponseEntity<Message> delete(Integer noticeId) {
        if (noticeId == null) {
            message.setMessage(null, 400,   "没有文件ID", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return noticeService.delete(noticeId);
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
                               HttpServletResponse response) throws UnsupportedEncodingException {
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
        System.out.println(noticePath);
        //获取文件名称
        String noticeName = notice.getNoticeName();

        // 如果文件名不为空，则进行下载
        if (noticeName != null) {
            //设置文件路径
            File file = new File(noticePath);
            // 如果文件名存在，则进行下载
            if (file.exists()) {
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename="
                        + URLEncoder.encode(noticeName,
                        "UTF-8"));
                // 实现文件下载
                byte[] buffer = new byte[1024];
                //输入流
                FileInputStream fileInputStream = null;
                //安全的输入流
                BufferedInputStream bufferedInputStream = null;
                try {
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    OutputStream outputStream = response.getOutputStream();
                    int i = bufferedInputStream.read(buffer);
                    while (i != -1) {
                        outputStream.write(buffer, 0, i);
                        i = bufferedInputStream.read(buffer);
                    }
                    System.out.println("Download the song successfully!");
                }
                catch (Exception e) {
                    System.out.println("Download the song failed!");
                }
                finally {
                    //关流
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        message.setMessage(null, 200, "文件下载成功", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
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
     * @param noticePatenId
     * @return
     */
    @RequestMapping("/searchManager")
    public ResponseEntity<Message>  searchmanagerId(Integer noticePatenId) {
        if ( noticePatenId == null) {
            message.setMessage(null, 400,   "没有文件ID", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return  noticeService.searchmanagerId(noticePatenId);

    }


}





