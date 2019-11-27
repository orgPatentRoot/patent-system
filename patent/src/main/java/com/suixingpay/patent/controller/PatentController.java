package com.suixingpay.patent.controller;

import com.suixingpay.patent.annotation.UserLog;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "/patent", produces = "application/json; charset=utf-8")
public class PatentController {

    @Autowired
    private PatentService patentService;
    /**
     * 新建专利
     * @param patent
     * @return
     */
    @RequestMapping("/insertPatent")
    public ResponseEntity<Message> insertPatentController(@Valid @RequestBody Patent patent,
                                                          HttpServletRequest request) {
        Message message = new Message();
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getUserId() == null) {
            message.setMessage(null, 400, "用户没有登录", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        patent.setPatentWriter(user.getUserId()); //通过session设置创建人id
//        patent.setPatentCreatePerson(patent.getPatentCreatePerson()); //设置创建人id，测试用这行，可以自行修改数值，将上一行注释即可
        patent.setPatentSign(0); //设置审核状态为未审核
        patent.setPatentStatusId(0); //设置专利进度状态为新建专利
        patent.setPatentWriter(0);  //设置撰写人为待认领
        return patentService.insertPatentSevice(patent);
    }

    /**
     * 通过专利ID修改专利信息（编辑功能）
     * @param patent
     * @param request
     * @return
     */
    @RequestMapping("/updatePatent")
    public ResponseEntity<Message> updatePatentByCreatePersonController(@Valid @RequestBody Patent patent,
                                                                        HttpServletRequest request) {
        Patent patentCondition = new Patent();
        patentCondition.setPatentId(patent.getPatentId());
        patentCondition.setPatentBatch(patent.getPatentBatch());
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum());
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum());
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime());
        patentCondition.setPatentTechnicalContact(patent.getPatentTechnicalContact());
        patentCondition.setPatentApplyPerson(patent.getPatentApplyPerson());
        patentCondition.setPatentName(patent.getPatentName());
        patentCondition.setPatentType(patent.getPatentType());
        patentCondition.setPatentInventor(patent.getPatentInventor());
        patentCondition.setPatentRemarks(patent.getPatentRemarks());
        return patentService.updatePatentServiceByIdService(patent);
    }

    /**
     * 通过专利ID修改专利撰写人信息（认领功能）
     * @param patent
     * @return
     */
    @RequestMapping("/updatePatentById")
    public ResponseEntity<Message> updatePatentWriterByIDController(@RequestBody Patent patent,
                                                                    HttpServletRequest request) {
        Message message = new Message();
        Patent patentCondition  = new Patent();
        if (patent.getPatentId() == null) {
            message.setMessage(null, 400, "专利Id不能为空", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getUserId() == null) {
            message.setMessage(null, 400, "用户没有登录！", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        patentCondition.setPatentId(patent.getPatentId());
        patentCondition.setPatentStatusId(2); //认领成功将进度变为方案讨论
        patentCondition.setPatentWriter(user.getUserId()); //通过session设置撰写人id
        return patentService.updatePatentWriterByIdService(patentCondition);
    }

    /**
     * 管理员查看所有专利
     * @param patent
     * @return
     */
    @RequestMapping("/selectAllPatent")
    public ResponseEntity<Message> selectAllPatentController(@RequestBody Patent patent) {
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum()); //设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum()); //设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime()); //设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName()); //设置发明名称筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor()); //设置发明人筛选条件
        patentCondition.setPatentStatusId(patent.getPatentStatusId()); //设置专利进度筛选条件
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 管理员查看指标维度
     * @param patent
     * @return
     */
    @RequestMapping("/selectAllIndexWithPatent")
    public ResponseEntity<Message> selectAllIndexWithPatentController(@RequestBody Patent patent) {
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setIndexContent(patent.getIndexContent()); //设置指标内容筛选条件
        patentCondition.setPatentName(patent.getPatentName()); //设置发明名称筛选条件
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum()); //设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum()); //设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime()); //设置申请时间筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor()); //设置发明人筛选条件
        patentCondition.setPatentStatusId(patent.getPatentStatusId()); //设置专利进度筛选条件
        return patentService.selectPatentWithIndexService(patentCondition);
    }
    /**
     * 用户查看专利池查未认领的专利
     * @param patent
     * @return
     */
    @RequestMapping("/selectAllPatentByNoWriter")
    public ResponseEntity<Message> selectAllPatentNoWriterController(@RequestBody Patent patent) {
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum()); //设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum()); //设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime()); //设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName()); //设置发明名称筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor()); //设置发明人筛选条件
        patentCondition.setPatentStatusId(1); //设置专利进度筛选条件--1、发明初合
        patentCondition.setPatentWriter(0); //设置撰写人为0，未认领
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 个人新建专利模块筛选查询
     * @param patent
     * @param request
     * @return
     */
    @RequestMapping("/selectPatentByCreatePerson")
    public ResponseEntity<Message> selectPatentByCreatePersonController(@RequestBody Patent patent,
                                                                        HttpServletRequest request) {
        Message message = new Message();
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getUserId() == null) {
            message.setMessage(null, 400, "用户没有登录", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum()); //设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum()); //设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime()); //设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName()); //设置发明名称筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor()); //设置发明人筛选条件
        patentCondition.setPatentStatusId(0); //设置专利进度筛选条件--新建专利进度
        patentCondition.setPatentCreatePerson(user.getUserId()); //设置创建人筛选条件
//        patentCondition.setPatentCreatePerson(patent.getPatentCreatePerson());//测试用这行，可以自行修改数值，将上一行注释即可
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 个人认领专利审核阶段模块筛选查询
     * @param patent
     * @param request
     * @return
     */
    @RequestMapping("/selectPatentByWriterNeekCheck")
    public ResponseEntity<Message> selectPatentByWriterController(@RequestBody Patent patent,
                                                                  HttpServletRequest request) {
        Message message = new Message();
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getUserId() == null) {
            message.setMessage(null, 400, "用户没有登录", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum()); //设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum()); //设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime()); //设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName()); //设置发明名称筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor()); //设置发明人筛选条件
        patentCondition.setPatentStatusId(patent.getPatentStatusId()); //设置专利进度筛选条件
        patentCondition.setPatentWriter(user.getUserId()); //设置撰写人筛选条件
//        patentCondition.setPatentWriter(patent.getPatentWriter());//测试用这行，将上一行注释即可
        patentCondition.setSpecialCondition("patent_status_id IN (0,2,3,4,5)"); //设置需要的审核进度
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 个人认领专利数据维护阶段模块筛选查询
     * @param patent
     * @param request
     * @return
     */
    @RequestMapping("/selectPatentByWriterNoCheck")
    public ResponseEntity<Message> selectPatentByConditionController(@RequestBody Patent patent,
                                                                     HttpServletRequest request) {
        Message message = new Message();
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getUserId() == null) {
            message.setMessage(null, 400, "用户没有登录", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum()); //设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum()); //设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime()); //设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName()); //设置发明名称筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor()); //设置发明人筛选条件
        patentCondition.setPatentStatusId(patent.getPatentStatusId()); //设置专利进度筛选条件
        patentCondition.setPatentWriter(user.getUserId()); //设置撰写人筛选条件
//        patentCondition.setPatentWriter(patent.getPatentWriter());//测试用这行，将上一行注释即可
        patentCondition.setSpecialCondition("patent_status_id IN (6,7,8,9,10,11,12)"); //设置数据维护的进度
        return patentService.selectPatentService(patentCondition);
    }

    /**
     *通过专利Id查询专利信息
     * @param patent
     * @return
     */
    @RequestMapping("/selectPatentByPatentID")
    public ResponseEntity<Message> selectPatentByIDController(@RequestBody Patent patent) {
        Message message = new Message();
        if (patent.getPatentId() == null) {
            message.setMessage(null, 400, "专利Id不能为空", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        Patent patentCondition = new Patent(); //重置筛选条件
        patentCondition.setPatentId(patent.getPatentId()); //设置专利ID筛选条件
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 查找所有待审核的专利信息
     * @param patent
     * @return
     */
    @RequestMapping("/selectExamine")
    public ResponseEntity<Message> selectExamine(@RequestBody Patent patent) {
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum()); //设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum()); //设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime()); //设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName()); //设置发明名称筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor()); //设置发明人筛选条件
        patentCondition.setPatentSign(1); //设置审核状态为审核中
        patentCondition.setPatentStatusId(patent.getPatentStatusId()); //设置专利进度筛选条件
        patentCondition.setSpecialCondition("patent_status_id IN (0,2,3,4,5)"); //设置需要的审核进度
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 通过审核功能
     * @param patent
     * @return
     */
    @RequestMapping("/auditPass")
    public ResponseEntity<Message> updateSign(@RequestBody Patent patent) {
        Message message = new Message();
        if (patent.getPatentId() == null) {
            message.setMessage(null, 400, "专利Id不能为空", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        Patent patentCondition = new Patent();
        patentCondition.setPatentId(patent.getPatentId());
        return patentService.auditPass(patentCondition);

    }

    /**
     * 审核不通过
     * @param patent
     * @return
     */
    @RequestMapping("/auditFailed")
    public ResponseEntity<Message> auditFailed(@RequestBody Patent patent) {
        Message message = new Message();
        if (patent.getPatentId() == null) {
            message.setMessage(null, 400, "专利Id不能为空", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        Patent patentCondition = new Patent();
        patentCondition.setPatentId(patent.getPatentId());
        return patentService.auditFailed(patentCondition);
    }

    /**
     * 用户提交审核：把专利状态从未审核转为审核中(0->1)
     * @param patent
     * @return
     */
    @RequestMapping("/userSubmitAudit")
    public ResponseEntity<Message> userSubmitAudit(@RequestBody Patent patent) {
        Message message = new Message();
        if (patent.getPatentId() == null) {
            message.setMessage(null, 400, "专利Id不能为空", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        Patent patentCondition = new Patent();
        patentCondition.setPatentId(patent.getPatentId());
        return patentService.userSubmitAudit(patent);
    }

    /**
     * 用户回滚：将专利的审核未通过状态改为未审核（2->0)
     * @param patent
     * @return
     */
    @RequestMapping("/userRollBack")
    public ResponseEntity<Message> userRollBack(@RequestBody Patent patent) {
        Message message = new Message();
        if (patent.getPatentId() == null) {
            message.setMessage(null, 400, "专利Id不能为空", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        Patent patentCondition = new Patent();
        patentCondition.setPatentId(patent.getPatentId());
        return patentService.userRollBack(patent);
    }


    //数据维护阶段修改专利状态（初审状态之后才能修改）
    @RequestMapping("/userUpdateStatusId")
    public ResponseEntity<Message> updateStatusId(@RequestBody Patent patent) {
        Message message = new Message();
        if (patent.getPatentId() == null) {
            message.setMessage(null, 400, "专利Id不能为空", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        if (patent.getPatentStatusId() == null) {
            message.setMessage(null, 400, "专利进度不能为空", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        Patent patentCondition = new Patent();
        patentCondition.setPatentId(patent.getPatentId());
        patentCondition.setPatentStatusId(patent.getPatentStatusId());
        return patentService.updateStatusId(patentCondition);
    }

    /**
     * 导出数据
     * @param response
     * @param patent
     * @throws IOException
     */
    @RequestMapping("/excelDownloads")
    public ResponseEntity<Message> downloadAllClassmate(HttpServletResponse response,
                                                        @RequestBody Patent patent) throws IOException {
        return patentService.downloadPatent(response, patent);
    }
}
