package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Patent;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public interface PatentService {

    /**
     * 插入专利信息
     * @param patent
     * @return
     */
    Message insertPatentService(Patent patent, String[] indexContent);

    /**
     * 通过专利ID和(创建人或撰写人)修改专利信息
     * @param patent
     * @return
     */
    Message updatePatentServiceByIdService(Patent patent);

    /**
     * 管理员查看所有专利
     * @param patent
     * @return
     */
    Message selectAllPatentService(Patent patent);

    /**
     * 查询专利信息
     * 通过专利id查询、案件文号查询、申请号查询、申请日期查询、进度查询、发明人姓名查询、创建人id查询、撰写人id查询、其他条件
     * @param patent
     * @return
     */
    List<Patent> selectPatentService(Patent patent);

    /**
     * 指标维度查询
     * 通过指标内容、专利名称、案件文号查询、申请号查询、申请日期查询、进度查询、发明人姓名查询
     * @param patent
     * @return
     */
    Message selectPatentWithIndexService(Patent patent);

    /**
     * 用户查看专利池查未认领的专利
     * @param patent
     */
    Message selectAllPatentNoWriterService(Patent patent);

    /**
     * 个人新建专利模块筛选查询
     * @param patent
     * @return
     */
    Message selectPatentByCreatePersonService(Patent patent);

    /**
     * 个人认领专利审核阶段模块筛选查询
     * @param patent
     * @return
     */
    Message selectPatentByWriterService(Patent patent);

    /**
     * 个人认领专利数据维护阶段模块筛选查询
     * @param patent
     * @return
     */
    Message selectPatentByWriterNoCheckService(Patent patent);

    /**
     * 通过专利Id查询专利信息
     * @param patent
     * @return
     */
    Message selectPatentByIDService(Patent patent);

    /**
     * 查找所有待审核的专利信息
     * @param patent
     * @return
     */
    Message selectExamineService(Patent patent);

    /**
     * 通过专利Id修改撰写人信息（认领功能）
     * @param patent
     * @return
     */
    Message updatePatentWriterByIdService(Patent patent);

    /**
     * 审核通过功能
     * @param patent
     * @return
     */
    Message auditPass(Patent patent);

    /**
     * 审核不通过功能
     * @param patent
     * @return
     */
    Message auditFailed(Patent patent);

    /**
     * 用户提交审核功能
     * @param patent
     * @return
     */
    Message userSubmitAudit(Patent patent);

    /**
     * 数据维护阶段修改专利状态（初审状态及之后才能修改）
     * @param patent
     * @return
     */
    Message updateStatusId(Patent patent);

    /**
     * 导出文件
     * @param response
     * @param content
     * @param list
     * @throws IOException
     */
    void exportDeviceModelMsg(HttpServletResponse response, String content, List<Patent> list)throws  IOException;

}
