package com.suixingpay.patent.controller;

import com.suixingpay.patent.annotation.UserLog;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.to.PatentChangeParams;
import com.suixingpay.patent.to.PatentCheckParams;
import com.suixingpay.patent.service.PatentService;
import com.suixingpay.patent.util.ParamCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/patent", produces = "application/json; charset=utf-8")
public class PatentController {

    @Autowired
    private PatentService patentService;

    @Autowired
    private Message message;

    @Value("${patentNeedCheckStatus}")
    private Integer patentStatusId;

    /**
     * 新建专利
     * @param changeParams 前端传过来的数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/insertPatent")
    public Message insertPatentController(
            @RequestBody PatentChangeParams changeParams,
            HttpServletRequest request,  HttpServletResponse response) {
        Patent patent = changeParams.changeToPatent(); //获取前端值
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断是否普通用户登录
        if (!ParamCheck.userIsLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        patent.setPatentCreatePerson(user.getUserId()); //通过session设置创建人id
        message = patentService.insertPatentService(patent, changeParams.getIndexContent());
        response.setStatus(message.getStatus());
        return message;
    }

    /**
     * 通过专利ID修改专利信息（编辑功能）
     * @param changeParams
     * @param request
     * @param response
     * @return
     */
    @UserLog("编辑专利基本信息")
    @RequestMapping("/updatePatent")
    public Message updatePatentByCreatePersonController(
            @RequestBody PatentChangeParams changeParams,
            HttpServletRequest request, HttpServletResponse response) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否登录
        if (!ParamCheck.userIsLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //判断专利Id不能为空
        if (ParamCheck.idIsEmpty(message, changeParams.getPatentId())) {
            response.setStatus(message.getStatus());
            return message;
        }
        //日志流程功能需求
        request.getSession().setAttribute("patentId", changeParams.getPatentId().toString());
        //将前端参数封装成Patent
        Patent patent = changeParams.changeToPatent();
        //修改专利信息
        message = patentService.updatePatentServiceByIdService(patent);
        response.setStatus(message.getStatus());
        return message;
    }

    /**
     * 通过专利ID修改专利撰写人信息（认领功能）
     * @param patentId
     * @param request
     * @param response
     * @return
     */
    @UserLog("认领了专利")
    @RequestMapping("/updatePatentById")
    public Message updatePatentWriterByIdController(
            Integer patentId,
            HttpServletRequest request, HttpServletResponse response) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否登录
        if (!ParamCheck.userIsLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //判断专利Id不能为空
        if (ParamCheck.idIsEmpty(message, patentId)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //日志流程功能需求
        request.getSession().setAttribute("patentId", patentId.toString());
        //设置获取的信息
        Patent patentCondition  = new Patent();
        patentCondition.setPatentId(patentId);
        patentCondition.setPatentWriter(user.getUserId()); //通过session设置撰写人id
        return patentService.updatePatentWriterByIdService(patentCondition);
    }

    /**
     * 管理员查看所有专利
     * @param checkParams
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/selectAllPatent")
    public Message selectAllPatentController(
            @RequestBody PatentCheckParams checkParams,
            HttpServletRequest request, HttpServletResponse response) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否管理员登录
        if (!ParamCheck.userIsManagerLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //将前端筛选参数封装成Patent对象
        Patent patentCondition = checkParams.changeToPatent();
        patentCondition.setSpecialCondition("patent_status_id != 0");
        List<Patent> list = patentService.selectPatentService(patentCondition);
        message.setMessage(list, 200, "查询成功！", true);
        return message;
    }

    /**
     * 管理员查看指标维度
     * @param checkParams
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/selectAllIndexWithPatent")
    public Message selectAllIndexWithPatentController(
            @RequestBody PatentCheckParams checkParams,
            HttpServletRequest request, HttpServletResponse response) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否管理员登录
        if (!ParamCheck.userIsManagerLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //将前端筛选参数封装成Patent对象
        Patent patentCondition = checkParams.changeToPatent();
        patentCondition.setSpecialCondition("patent_status_id != 0");
        List<Patent> list = patentService.selectPatentWithIndexService(patentCondition);
        message.setMessage(list, 200, "查询成功！", true);
        return message;
    }

    /**
     * 用户查看专利池查未认领的专利
     * @param checkParams
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/selectAllPatentByNoWriter")
    public Message selectAllPatentNoWriterController(
            @RequestBody PatentCheckParams checkParams,
            HttpServletRequest request, HttpServletResponse response) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断是否普通用户登录
        if (!ParamCheck.userIsLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //将前端筛选参数封装成Patent对象
        Patent patentCondition = checkParams.changeToPatent();
        patentCondition.setPatentStatusId(1); //设置专利进度筛选条件--1、发明初合
        patentCondition.setPatentWriter(0); //设置撰写人为0，未认领
        List<Patent> list = patentService.selectPatentService(patentCondition);
        message.setMessage(list, 200, "查询成功！", true);
        return message;
    }

    /**
     * 个人新建专利模块筛选查询
     * @param checkParams
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/selectPatentByCreatePerson")
    public Message selectPatentByCreatePersonController(
            @RequestBody PatentCheckParams checkParams,
            HttpServletRequest request, HttpServletResponse response) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断是否普通用户登录
        if (!ParamCheck.userIsLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //将前端筛选参数封装成Patent对象
        Patent patentCondition = checkParams.changeToPatent();
        patentCondition.setPatentStatusId(0); //设置专利进度筛选条件--新建专利进度
        patentCondition.setPatentCreatePerson(user.getUserId()); //设置创建人筛选条件
        List<Patent> list = patentService.selectPatentService(patentCondition);
        message.setMessage(list, 200, "查询成功！", true);
        return message;
    }

    /**
     * 个人认领专利审核阶段模块筛选查询
     * @param checkParams
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/selectPatentByWriterNeekCheck")
    public Message selectPatentByWriterController(
            @RequestBody PatentCheckParams checkParams,
            HttpServletRequest request, HttpServletResponse response) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断是否普通用户登录
        if (!ParamCheck.userIsLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //将前端筛选参数封装成Patent对象
        Patent patentCondition = checkParams.changeToPatent();
        patentCondition.setPatentWriter(user.getUserId()); //设置撰写人筛选条件
        //设置需要的审核进度
        patentCondition.setSpecialCondition("patent_status_id not in(0,1) and patent_status_id <= " + patentStatusId);
        List<Patent> list = patentService.selectPatentService(patentCondition);
        message.setMessage(list, 200, "查询成功！", true);
        return message;
    }

    /**
     * 个人认领专利数据维护阶段模块筛选查询
     * @param checkParams
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/selectPatentByWriterNoCheck")
    public Message selectPatentByConditionController(
            @RequestBody PatentCheckParams checkParams,
            HttpServletRequest request, HttpServletResponse response) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断是否普通用户登录
        if (!ParamCheck.userIsLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //将前端筛选参数封装成Patent对象
        Patent patentCondition = checkParams.changeToPatent();
        patentCondition.setPatentWriter(user.getUserId()); //设置撰写人筛选条件
        patentCondition.setSpecialCondition("patent_status_id > " + patentStatusId); //设置数据维护的进度
        List<Patent> list = patentService.selectPatentService(patentCondition);
        message.setMessage(list, 200, "查询成功！", true);
        return message;
    }

    /**
     * 通过专利Id查询专利信息
     * @param patentId
     * @param response
     * @return
     */
    @RequestMapping("/selectPatentByPatentId")
    public Message selectPatentByIDController(
            Integer patentId,
            HttpServletResponse response) {
        //判断专利Id不能为空
        if (ParamCheck.idIsEmpty(message, patentId)) {
            response.setStatus(message.getStatus());
            return message;
        }
        Patent patentCondition = new Patent(); //重置筛选条件
        patentCondition.setPatentId(patentId); //设置专利ID筛选条件
        List<Patent> list = patentService.selectPatentService(patentCondition);
        message.setMessage(list, 200, "查询成功！", true);
        return message;
    }

    /**
     * 查找所有待审核的专利信息
     * @param checkParams
     * @return
     */
    @RequestMapping("/selectExamine")
    public Message selectExamine(
            @RequestBody PatentCheckParams checkParams,
            HttpServletRequest request, HttpServletResponse response) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否管理员登录
        if (!ParamCheck.userIsManagerLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //将前端参数形成筛选条件
        Patent patentCondition = checkParams.changeToPatent();
        patentCondition.setPatentSign(1); //设置审核状态为审核中
        //设置需要的审核进度
        patentCondition.setSpecialCondition("patent_status_id not in(1) and patent_status_id <= " + patentStatusId);
        List<Patent> list = patentService.selectPatentService(patentCondition);
        message.setMessage(list, 200, "查询成功！", true);
        return message;
    }

    /**
     * 通过审核通过功能
     * @param patentId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/auditPass")
    public Message updateSign(
            Integer patentId,
            HttpServletRequest request, HttpServletResponse response) {
        Message message = new Message();
        //判断专利Id不能为空
        if (ParamCheck.idIsEmpty(message, patentId)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否管理员登录
        if (!ParamCheck.userIsManagerLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        Patent patentCondition = new Patent();
        patentCondition.setPatentId(patentId);
        message = patentService.auditPass(patentCondition);
        response.setStatus(message.getStatus());
        return message;
    }

    /**
     * 管理员驳回
     * @param patentId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/auditFailed")
    public Message auditFailed(
            Integer patentId,
            HttpServletRequest request, HttpServletResponse response) {
        Message message = new Message();
        //判断专利Id不能为空
        if (ParamCheck.idIsEmpty(message, patentId)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否管理员登录
        if (!ParamCheck.userIsManagerLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        Patent patentCondition = new Patent();
        patentCondition.setPatentId(patentId);
        message = patentService.auditFailed(patentCondition);
        response.setStatus(message.getStatus());
        return message;
    }

    /**
     * 用户提交审核：把专利状态从未审核转为审核中(0->1)
     * @param patentId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userSubmitAudit")
    public Message userSubmitAudit(
            Integer patentId,
            HttpServletRequest request, HttpServletResponse response) {
        Message message = new Message();
        //判断专利Id不能为空
        if (ParamCheck.idIsEmpty(message, patentId)) {
            response.setStatus(message.getStatus());
            return message;
        }
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断是否普通用户登录
        if (!ParamCheck.userIsLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        Patent patentCondition = new Patent();
        patentCondition.setPatentId(patentId);
        message = patentService.userSubmitAudit(patentCondition);
        response.setStatus(message.getStatus());
        return message;
    }

    /**
     * 数据维护阶段修改专利状态（设定的进度之后才能修改）
     */
    @RequestMapping("/userUpdateStatusId")
    public Message updateStatusId(
            @Valid @RequestBody PatentCheckParams checkParams,
            HttpServletRequest request, HttpServletResponse response) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断是否普通用户登录
        if (!ParamCheck.userIsLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        Patent patentCondition = new Patent();
        patentCondition.setPatentId(checkParams.getPatentId());
        patentCondition.setPatentStatusId(checkParams.getPatentStatusId());
        message = patentService.updateStatusId(patentCondition);
        response.setStatus(message.getStatus());
        return message;
    }

    /**
     * 专利导出数据
     * @param response
     * @param patent
     * @throws IOException
     */
    @RequestMapping("/excelDownloads")
    public Message downloadAllClassmate(
            @RequestBody Patent patent,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否管理员登录
        if (!ParamCheck.userIsManagerLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        List<Patent> list = patentService.selectPatentService(patent);
        patentService.exportDeviceModelMsg(response, "patentExcel", list);
        message.setMessage("http://172.16.43.117:8080/patentExcel.xls", 200, "导出成功！", true);
        return message;
    }

    /**
     * 指标维度导出数据
     * @param response
     * @param patent
     * @throws IOException
     */
    @RequestMapping("/indexDownloads")
    public Message indexDownloadAllClassmate(
            @RequestBody Patent patent,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否管理员登录
        if (!ParamCheck.userIsManagerLogin(message, user)) {
            response.setStatus(message.getStatus());
            return message;
        }
        List<Patent> list = patentService.selectPatentWithIndexService(patent);
        patentService.exportDeviceModelMsg(response, "patentWithIndexExcel", list);
        message.setMessage("http://172.16.43.117:8080/patentWithIndexExcel.xls", 200, "导出成功！", true);
        return message;
    }
}
