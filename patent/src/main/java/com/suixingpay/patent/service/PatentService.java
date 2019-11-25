package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Patent;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import java.util.List;

@Service
public interface PatentService {

    /**
     * 插入专利信息
     * @param patent
     * @return
     */
    public String insertPatentSevice(Patent patent);

    /**
     * 通过专利ID和(创建人或撰写人)修改专利信息
     * @param patent
     * @return
     */
    public String updatePatentServiceByIdService(Patent patent);

    /**
     * 通过专利Id修改撰写人信息（认领功能）
     * @param patent
     * @return
     */
    public String updatePatentWriterByIdService(Patent patent);

    /**
     * 查询专利信息
     * 通过专利id查询、案件文号查询、申请号查询、申请日期查询、进度查询、发明人姓名查询、创建人id查询、撰写人id查询、其他条件
     * @param patent
     * @return
     */
    public List<Patent> selectPatentService(Patent patent);

    /**
     * 审核通过功能
     * @param patent
     * @return
     */
    String auditPass(Patent patent);

    /**
     * 审核不通过功能
     * @param patent
     * @return
     */
    String auditFailed(Patent patent);

    /**
     * 用户提交审核功能
     * @param patent
     * @return
     */
    String UserSubmitAudit (Patent patent);

    /**
     * 用户回滚功能
     * @param patent
     * @return
     */
    String UserRollBack(Patent patent);

    /**
     * 数据维护阶段修改专利状态（初审状态及之后才能修改）
     * @param patent
     * @return
     */
    String updateStatusId(Patent patent);
}
