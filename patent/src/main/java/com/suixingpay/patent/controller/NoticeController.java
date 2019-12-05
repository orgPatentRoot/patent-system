package com.suixingpay.patent.controller;


import com.suixingpay.patent.annotation.UserLog;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.service.NoticeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import com.suixingpay.patent.pojo.Notice;

@RestController         //返回json
@RequestMapping("/file")//配置映射
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    private Message message = new Message();



    @UserLog("上传交底书！")
    @RequestMapping("/upload")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)



    /**
     * 交底书上传
     */
    public ResponseEntity<Message> upload(@RequestParam("patentId") Integer patentId,
                                          @RequestParam("file") MultipartFile[] fils, HttpServletRequest request) {
        return noticeService.insert(patentId, fils, request);
    }






    /**
     *交底书下载
     * @param noticeId  文件的id主键自增长
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/download")
    public ResponseEntity<Message>  downloadFile(@Param("noticeId") Integer noticeId,
            HttpServletResponse response) throws UnsupportedEncodingException, UnknownHostException {
        //判断交底书ID
        if (noticeId == null) {
            message.setMessage(null, 400,   "没有需要下载文件ID", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        //返回数据库内的所有信息
        Notice notice = noticeService.selectNoticeByPatentId(noticeId);
        if (notice == null) {
            message.setMessage(null, 400, "文件不存在", false);
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
        //判断交底书ID是否存在
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
        //判断交底书专利ID是否存在
        if (noticePatentId == null) {
            message.setMessage(null, 400,   "没有文件ID", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return  noticeService.selectByPatentId(noticePatentId);
    }





    /**
     * 管理员查看
     * @param noticePatentId
     * @return
     */
    @RequestMapping("/searchManager")
    public ResponseEntity<Message>  searchmanagerId(Integer noticePatentId) {
        //判断交底书专利是否存在
        if (noticePatentId == null) {
            message.setMessage(null, 400,   "没有文件ID", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        return  noticeService.searchmanagerId(noticePatentId);
    }
}





