package com.suixingpay.patent.to;

import com.suixingpay.patent.pojo.Patent;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description 专利筛选查询参数组装
 * @Author 朱金圣[zhu_js@suixingpay.com]
 * @Date 2019/12/2 16:00
 * @Version 1.0
 */
@Getter
@Setter
public class PatentCheckParams {
    @NotNull(message = "专利Id不能为空！")
    private Integer patentId; //专利Id

    private String patentCaseNum; //案例文号

    private String patentApplyNum; //申请号

    private Date patentApplyTime; //申请时间

    private String patentApplyPerson; //申请人中文名称

    private String patentName; //专利中文名称

    @NotNull(message = "专利进度不能为空！")
    private Integer patentStatusId; //专利进度状态

    private String patentInventor; //发明人中文名称

    private Integer indexId; //指标Id

    private  String indexContent; //指标内容

    /**
     * 将查询参数返成一个patent
     * @return
     */
    public Patent changeToPatent() {
        Patent patent = new Patent();
        patent.setPatentId(patentId);
        patent.setPatentCaseNum(patentCaseNum);
        patent.setPatentApplyNum(patentApplyNum);
        patent.setPatentApplyTime(patentApplyTime);
        patent.setPatentApplyPerson(patentApplyPerson);
        patent.setPatentName(patentName);
        patent.setPatentStatusId(patentStatusId);
        patent.setIndexId(indexId);
        patent.setIndexContent(indexContent);
        return patent;
    }
}
