package com.suixingpay.patent.controller;

import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.service.PatentService;
import com.suixingpay.patent.service.UserService;
import com.suixingpay.patent.util.ParamCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/patent")
@RequestMapping(value="/patent",produces="application/json; charset=utf-8")
public class PatentController {

    @Autowired
    private PatentService patentService;
    private UserService userService;
    /**
     * 新建专利
     * @param patent
     * @return
     */
    @RequestMapping("/insertPatent")
    public String insertPatentController(@Valid @RequestBody Patent patent,HttpServletRequest request) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
//        patent.setPatentWriter(user.getUserId());//通过session设置创建人id
        patent.setPatentCreatePerson(patent.getPatentCreatePerson());//设置创建人id，测试用这行，可以自行修改数值，将上一行注释即可
        patent.setPatentSign(0);//设置审核状态为未审核
        patent.setPatentStatusId(0);//设置专利进度状态为新建专利
        patent.setPatentWriter(0);//设置撰写人为待认领
        return patentService.insertPatentSevice(patent);
    }

    /**
     * 通过专利ID和创建人修改专利信息（新建专利模块编辑功能）
     * @param patent
     * @param request
     * @return
     */
    @RequestMapping("/updatePatentByCreatePerson")
    public String updatePatentByCreatePersonController(@Valid @RequestBody Patent patent,HttpServletRequest request){
        patent.setPatentWriter(null);//sql是动态sql会根据创建人或撰写人动态拼接，所以这将撰写人设为null
        User user = (User) request.getSession().getAttribute("user");
        patent.setPatentCreatePerson(user.getUserId());//通过session设置创建人id
//        patent.setPatentCreatePerson(1);//设置创建人id，测试用这行，可以自行修改数值，将上一行注释即可
        return patentService.updatePatentServiceByIdService(patent);
    }

    /**
     * 通过专利ID和撰写人修改专利信息（个人认领模块编辑功能）
     * @param patent
     * @param request
     * @return
     */
    @RequestMapping("/updatePatentByWriter")
    public String updatePatentByWriterController(@Valid @RequestBody Patent patent,HttpServletRequest request){
        patent.setPatentCreatePerson(null);//sql是动态sql会根据创建人或撰写人动态拼接，所以这将创建人设为null
        User user = (User) request.getSession().getAttribute("user");
        patent.setPatentWriter(user.getUserId());//通过session设置撰写人id
//        patent.setPatentWriter(1);//设置撰写人id，测试用这行，可以自行修改数值，将上一行注释即可
        return patentService.updatePatentServiceByIdService(patent);
    }

    /**
     * 通过专利ID修改专利撰写人信息（认领功能）
     * @param patent
     * @return
     */
    @RequestMapping("/updatePatentById")
    public String updatePatentWriterByIDController(@Valid @RequestBody Patent patent){
        return patentService.updatePatentServiceByIdService(patent);
    }
    /**
     * 查看所有专利
     * @param patent
     * @return
     */
    @RequestMapping("/selectAllPatent")
    public List<Patent> selectAllPatentController(@RequestBody Patent patent) {
        Patent patentCondition = new Patent();
        //分页直接查询所有专利--一页显示15条信息
        ParamCheck.pageParamCheck(patent.getCurrentPage());//检查页数是否合法
        patentCondition.setCurrentPage((patent.getCurrentPage()-1)*15);
        return patentService.selectPatentService(patentCondition);
    }
    /**
     * 用户查看专利池查未认领的专利
     * @param request
     * @return
     */
    @RequestMapping("/selectAllPatentByNoWriter")
    public List<Patent> selectAllPatentNoWriterController(@RequestBody Patent patent,HttpServletRequest request) {
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum());//设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum());//设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime());//设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName());//设置发明名称筛选条件
        System.out.println(patent.getPatentName());
        patentCondition.setPatentInventor(patent.getPatentInventor());//设置发明人筛选条件
        patentCondition.setPatentStatusId(1);//设置专利进度筛选条件--1、发明初合
        patentCondition.setPatentWriter(0);//设置撰写人为0，未认领
        //分页直接查询所有专利--一页显示15条信息
        ParamCheck.pageParamCheck(patent.getCurrentPage());//检查页数是否合法
        patentCondition.setCurrentPage((patent.getCurrentPage()-1)*15);
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 个人新建专利模块筛选查询
     * @param patent
     * @param request
     * @return
     */
    @RequestMapping("/selectPatentByCreatePerson")
    public List<Patent> selectPatentByCreatePersonController(@RequestBody Patent patent, HttpServletRequest request) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum());//设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum());//设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime());//设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName());//设置发明名称筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor());//设置发明人筛选条件
        patentCondition.setPatentStatusId(0);//设置专利进度筛选条件--新建专利进度
        patentCondition.setPatentCreatePerson(user.getUserId());//设置创建人筛选条件
//        patentCondition.setPatentCreatePerson(patent.getPatentCreatePerson());//测试用这行，可以自行修改数值，将上一行注释即可
        //分页直接查询所有专利--一页显示15条信息
        ParamCheck.pageParamCheck(patent.getCurrentPage());//检查页数是否合法
        patentCondition.setCurrentPage((patent.getCurrentPage()-1)*15);
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 个人认领专利审核阶段模块筛选查询
     * @param patent
     * @param request
     * @return
     */
    @RequestMapping("/selectPatentByWriterNeekCheck")
    public List<Patent> selectPatentByWriterController(@RequestBody Patent patent, HttpServletRequest request) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum());//设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum());//设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime());//设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName());//设置发明名称筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor());//设置发明人筛选条件
        patentCondition.setPatentStatusId(patent.getPatentStatusId());//设置专利进度筛选条件
//        patentCondition.setPatentWriter(user.getUserId());//设置撰写人筛选条件
        patentCondition.setPatentWriter(patent.getPatentWriter());//测试用这行，将上一行注释即可
        patentCondition.setSpecialCondition("patent_status_id IN (0,2,3,4,5)");//设置需要的审核进度
        //分页直接查询所有专利--一页显示15条信息
        ParamCheck.pageParamCheck(patent.getCurrentPage());//检查页数是否合法
        patentCondition.setCurrentPage((patent.getCurrentPage()-1)*15);
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 个人认领专利数据维护阶段模块筛选查询
     * @param patent
     * @param request
     * @return
     */
    @RequestMapping("/selectPatentByWriterNoCheck")
    public List<Patent> selectPatentByConditionController(@RequestBody Patent patent,HttpServletRequest request) {
        //从session获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum());//设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum());//设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime());//设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName());//设置发明名称筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor());//设置发明人筛选条件
        patentCondition.setPatentStatusId(patent.getPatentStatusId());//设置专利进度筛选条件
        patentCondition.setPatentWriter(user.getUserId());//设置撰写人筛选条件
//        patentCondition.setPatentWriter(patent.getPatentWriter());//测试用这行，将上一行注释即可
        patentCondition.setSpecialCondition("patent_status_id IN (6,7,8,9,10,11,12)");//设置数据维护的进度
        //分页直接查询所有专利--一页显示15条信息
        ParamCheck.pageParamCheck(patent.getCurrentPage());//检查页数是否合法
        patentCondition.setCurrentPage((patent.getCurrentPage()-1)*15);
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 通过专利Id查看专利信息
     * @param patent
     * @return
     */
    @RequestMapping("/selectPatentByPatentID")
    public List<Patent> selectPatentByIDController(@RequestBody Patent patent) {
        //获取前端传过来的专利ID信息
        Integer patentId = patent.getPatentId();
        if(patentId!=null){//确定id不为null
            patent = new Patent();//重置筛选条件
            patent.setPatentId(patentId);//设置专利ID筛选条件
            patent.setCurrentPage(0);//设置页数为1，limit 0,15，sql语句需求必须设置的参数
            return patentService.selectPatentService(patent);
        }
        List<Patent> patents = new ArrayList<>();
        return patents;
    }

    /**
     * 查找所有待审核的专利信息
     * @param patent
     * @return
     */
    @RequestMapping("/selectExamine")
    public List<Patent> selectExamine(@RequestBody Patent patent){
        //设置筛选条件
        Patent patentCondition = new Patent();
        patentCondition.setPatentCaseNum(patent.getPatentCaseNum());//设置案例文号筛选条件
        patentCondition.setPatentApplyNum(patent.getPatentApplyNum());//设置申请号筛选条件
        patentCondition.setPatentApplyTime(patent.getPatentApplyTime());//设置申请时间筛选条件
        patentCondition.setPatentName(patent.getPatentName());//设置发明名称筛选条件
        patentCondition.setPatentInventor(patent.getPatentInventor());//设置发明人筛选条件
        patentCondition.setPatentSign(1);//设置审核状态为审核中
        patentCondition.setPatentStatusId(patent.getPatentStatusId());//设置专利进度筛选条件
        patentCondition.setSpecialCondition("patent_status_id IN (0,2,3,4,5)");//设置需要的审核进度
        //分页直接查询所有专利--一页显示15条信息
        ParamCheck.pageParamCheck(patent.getCurrentPage());//检查页数是否合法
        patentCondition.setCurrentPage((patent.getCurrentPage()-1)*15);
        return patentService.selectPatentService(patentCondition);
    }

    /**
     * 通过审核功能
     * @param patent
     * @return
     */
    @RequestMapping("/auditPass")
    public String updateSign(@RequestBody Patent patent){
        Patent patentCondition = new Patent();
        if(patent.getPatentId()==null){
            throw new RuntimeException("专利Id不能为空");
        }
        patentCondition.setPatentId(patent.getPatentId());
        patentCondition.setCurrentPage(0);
        return patentService.auditPass(patentCondition);

    }

    /**
     * 审核不通过
     * @param patent
     * @return
     */
    @RequestMapping("/auditFailed")
    public String auditFailed(@RequestBody Patent patent){
        Patent patentCondition = new Patent();
        if(patent.getPatentId()==null){
            throw new RuntimeException("专利Id不能为空");
        }
        patentCondition.setPatentId(patent.getPatentId());
        patentCondition.setCurrentPage(0);
        return patentService.auditFailed(patentCondition);
    }

    /**
     * 用户提交审核：把专利状态从未审核转为审核中(0->1)
     * @param patent
     * @return
     */
    @RequestMapping("/UserSubmitAudit")
    public String UserSubmitAudit(@RequestBody Patent patent){
        Patent patentCondition = new Patent();
        if(patent.getPatentId()==null){
            throw new RuntimeException("专利Id不能为空");
        }
        patentCondition.setPatentId(patent.getPatentId());
        patentCondition.setCurrentPage(0);
        return patentService.UserSubmitAudit(patent);
    }

    /**
     * 用户回滚：将专利的审核未通过状态改为未审核（2->0)
     * @param patent
     * @return
     */
    @RequestMapping("/UserRollBack")
    public String UserRollBack(@RequestBody Patent patent){
        Patent patentCondition = new Patent();
        if(patent.getPatentId()==null){
            throw new RuntimeException("专利Id不能为空");
        }
        patentCondition.setPatentId(patent.getPatentId());
        patentCondition.setCurrentPage(0);
        return patentService.UserRollBack(patent);
    }


    //数据维护阶段修改专利状态（初审状态之后才能修改）
    @RequestMapping("/UserUpdateStatusId")
    public String updateStatusId(@RequestBody Patent patent){
        Patent patentCondition = new Patent();
        if(patent.getPatentId()==null){
            throw new RuntimeException("专利Id不能为空");
        }
        if(patent.getPatentStatusId()==null){
            throw new RuntimeException("专利进度不能为空");
        }
        patentCondition.setPatentId(patent.getPatentId());
        patentCondition.setPatentStatusId(patent.getPatentStatusId());
        patentCondition.setCurrentPage(0);
        return patentService.updateStatusId( patentCondition);
    }
}
