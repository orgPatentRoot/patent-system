package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.PatentMapper;
import com.suixingpay.patent.mapper.UserMapper;
import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.pojo.User;
import com.suixingpay.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatentServiceImpl implements PatentService {

    @Autowired
    private PatentMapper patentMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 插入专利信息
     * @param patent
     * @return
     */
    @Override
    public String insertPatentSevice(Patent patent) {
        User user = userMapper.selectUserByUserId(patent.getPatentCreatePerson());
        if(user!=null){
            if(patentMapper.insertPatent(patent)!=0)
                return "新建专利成功";
        }else {
            return "创建人不存在，新建专利失败";
        }
        return "新建专利失败";
    }

    /**
     * 通过专利ID和(创建人或撰写人)修改专利信息
     * @param patent
     * @return
     */
    @Override
    public String updatePatentServiceByIdService(Patent patent) {
        if(patentMapper.updatePatent(patent)!=0)
            return "修改信息成功";
        return "修改信息失败";
    }

    /**
     * 通过专利Id修改撰写人信息（认领功能）
     * @param patent
     * @return
     */
    @Override
    public String updatePatentWriterByIdService(Patent patent) {
        if(patentMapper.updatePatentWriterByID(patent)!=0)
            return "认领成功！";
        return "认领失败！";
    }

    /**
     * 查询专利信息
     * 通过专利id查询、案件文号查询、申请号查询、申请日期查询、进度查询、发明人姓名查询、创建人id查询、撰写人id查询、其他条件
     * @param patent
     * @return
     */
    @Override
    public List<Patent> selectPatentService(Patent patent) {
        List<Patent> list = patentMapper.selectPatent(patent);
        //设置总页数在第一条记录里
        if(list.size()>0){
            if(patent.getCurrentPage()==0){//如果是第一次查询则查询总页数并将总数放在第一条数据里返回前端
                Patent patent1 = list.get(0);
                Integer pagecount = patentMapper.selectPatentCount(patent).getPageCount();
                patent1.setPageCount(pagecount/15==0?1:pagecount/15);
                list.set(0,patent1);
            }
        }
        return list;
    }

    /**
     * 审核通过
     * @param patent
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @Override
    public String auditPass(Patent patent) {
        List<Patent> list = patentMapper.selectPatent(patent);
        if(list.size()<1)
            throw new RuntimeException("专利Id不存在！");
        patent.setPatentStatusId(list.get(0).getPatentStatusId());
        if(list.get(0).getPatentSign()!=1)
            throw new RuntimeException("专利不属于审核中状态！");
        if(patent.getPatentStatusId()>=6)
            throw new RuntimeException("专利进度不属于审核阶段");
        //审核通过将审核状态设为待审核状态0，并且将专利进度+1
        patent.setPatentSign(0);
        patent.setPatentStatusId(patent.getPatentStatusId()+1);
        if(patentMapper.updatePatentDudit(patent)==0)
            throw new RuntimeException("修改专利审核状态失败！");
        if(patentMapper.updateStatusId(patent)==0)
            throw new RuntimeException("修改专利进度失败！");
        return "审核通过成功！";
    }

    /**
     * 审核不通过
     * @param patent
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @Override
    public String auditFailed(Patent patent) {
        List<Patent> list = patentMapper.selectPatent(patent);
        if(list.size()<1)
            throw new RuntimeException("专利Id不存在！");
        if(list.get(0).getPatentSign()!=1)
            throw new RuntimeException("专利不属于审核中状态！");
        if(list.get(0).getPatentStatusId()>=6)
            throw new RuntimeException("专利进度不属于审核阶段！");
        //审核不通过 将审核状态设为审核不通过状态2
        patent.setPatentSign(2);
        //设置条件:审核进度为审核中1,专利进度为需要审核的阶段
        patent.setSpecialCondition("patent_sign = 1 and patent_status_id IN (0,2,3,4,5)");//
        if(patentMapper.updatePatentDudit(patent)==0)
            throw new RuntimeException("修改审核状态失败！");
        return "审核不通过成功！";
    }

    /**
     * 用户提交审核功能
     * @param patent
     * @return
     */
    @Override
    public String UserSubmitAudit(Patent patent) {
        List<Patent> list = patentMapper.selectPatent(patent);
        if(list.size()<1)
            throw new RuntimeException("专利Id不存在！");
        patent.setPatentStatusId(list.get(0).getPatentStatusId());
        if(list.get(0).getPatentSign()!=0)
            throw new RuntimeException("专利不属于待审核状态！");
        if(patent.getPatentStatusId()>=6)
            throw new RuntimeException("专利进度不属于审核阶段");
        //提交审核:将审核状态设为审核中1
        patent.setPatentSign(0);
        //设置条件:审核进度为审核中0,专利进度为需要审核的阶段
        patent.setSpecialCondition("patent_sign = 0 and patent_status_id IN (0,2,3,4,5)");//
        if(patentMapper.updatePatentDudit(patent)==0)
            throw new RuntimeException("提交审核时修改审核状态失败！");
        return "提交审核成功！";
    }

    /**
     * 用户回滚功能
     * @param patent
     * @return
     */
    @Override
    public String UserRollBack(Patent patent) {
        List<Patent> list = patentMapper.selectPatent(patent);
        if(list.size()<1)
            throw new RuntimeException("专利Id不存在！");
        patent.setPatentStatusId(list.get(0).getPatentStatusId());
        if(list.get(0).getPatentSign()!=2)
            throw new RuntimeException("专利不属于审核不通过状态！");
        if(patent.getPatentStatusId()>=6)
            throw new RuntimeException("专利进度不属于审核阶段");
        //提交审核:将审核状态设为待审核0
        patent.setPatentSign(0);
        //设置条件:审核进度为审核中2,专利进度为需要审核的阶段
        patent.setSpecialCondition("patent_sign = 2 and patent_status_id IN (0,2,3,4,5)");//
        if(patentMapper.updatePatentDudit(patent)==0)
            throw new RuntimeException("回滚时修改审核状态失败！");
        return "提交审核成功！";
    }

    /**
     * 数据维护修改专利状态（初审状态及之后才能修改）
     * @param patent
     * @return
     */
    @Override
    public String updateStatusId(Patent patent) {
        Integer patentStatusId = patent.getPatentStatusId();
        patent.setPatentStatusId(null);
        List<Patent> list = patentMapper.selectPatent(patent);
        if(list.size()<1)
            throw new RuntimeException("专利Id不存在！");
        //将进度状态修改为用户选择的状态
        patent.setPatentStatusId(patentStatusId);
        System.out.println(patentStatusId);
        patent.setSpecialCondition("patent_status_id IN (6,7,8,9,10,11,12)");//设置数据维护阶段可以修改的进度
        if(patentMapper.updateStatusId(patent)==0)
            throw new RuntimeException("专利进度不属于数据维护阶段！");
        return "修改专利进度成功！";
    }

}
