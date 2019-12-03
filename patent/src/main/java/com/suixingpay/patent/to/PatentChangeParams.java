package com.suixingpay.patent.to;

import com.suixingpay.patent.pojo.Patent;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Description 专利对接前端传来的插入、修改数据
 * @Author 朱金圣[zhu_js@suixingpay.com]
 * @Date 2019/12/2 17:28
 * @Version 1.0
 */
@Setter
@Getter
public class PatentChangeParams {
    private Integer patentId; //专利Id

    private String patentBatch; //批数

    private String patentCaseNum; //案例文号

    private String patentApplyNum; //申请号

    private Date patentApplyTime; //申请时间

    private String patentTechnicalContact; //技术联系人中文名称

    private String patentApplyPerson; //申请人中文名称

    private String patentName; //专利中文名称

    private String patentType; //发明类型

    private String patentInventor; //发明人中文名称

    private String patentRemarks; //备注

    private String[] indexContent; //指标内容

    /**
     * 将前端数据封装成后端需要的Patent对象
     * @return
     */
    public Patent changeToPatent(){
        Patent patent = new Patent();
        patent.setPatentId(patentId);
        patent.setPatentBatch(patentBatch);
        patent.setPatentCaseNum(patentCaseNum);
        patent.setPatentApplyNum(patentApplyNum);
        patent.setPatentApplyTime(patentApplyTime);
        patent.setPatentTechnicalContact(patentTechnicalContact);
        patent.setPatentApplyPerson(patentApplyPerson);
        patent.setPatentName(patentName);
        patent.setPatentType(patentType);
        patent.setPatentInventor(patentInventor);
        patent.setPatentRemarks(patentRemarks);
        return patent;
    }
}
