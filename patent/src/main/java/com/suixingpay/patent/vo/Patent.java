package com.suixingpay.patent.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Date;


@Getter
@Setter
@ToString
@Component
public class Patent {
    private int patentId;//专利主键
    private String patentBatch;//专利批次
    private String patentCaseNum;//案例文号
    private String patentApplyNum;//申请号
    private Date patentApplyTime;//申请时间
    private String patentTechnicalContact;//技术联系人
    private String patentApplyPerson;//申请人
    private int patentCreatePerson;//专利创建人
    private String patentName;//专利名
    private int patentSign;//是否需要审核
    private int patentStatusId;//专利状态
    private String patentType;//发明类型
    private String patentInventor;//发明人
    private int patentWriter;//撰写人
    private String patentRemarks;//备注
}
