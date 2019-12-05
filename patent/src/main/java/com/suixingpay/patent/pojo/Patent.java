package com.suixingpay.patent.pojo;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Patent {

    private Integer patentId; //专利Id

    private String patentBatch; //批数

    private String patentCaseNum; //案例文号

    private String patentApplyNum; //申请号

    private Date patentApplyTime; //申请时间

    private String patentTechnicalContact; //技术联系人中文名称

    private String patentApplyPerson; //申请人中文名称

    private Integer patentCreatePerson; //创建人Id

    private String patentName; //专利中文名称

    private Integer patentSign; //专利审核状态

    private Integer patentStatusId; //专利进度状态

    private String patentType; //发明类型

    private String patentInventor; //发明人中文名称

    private Integer patentWriter; //撰写人Id

    private String patentRemarks; //备注

    private String statusName; //进度名称

    private String createPersonName; //创建人名字

    private String writerName; //撰写人名字

    private Integer indexId; //指标Id

    private  String indexContent; //指标内容

    private  Date indexCreateTime; //创建时间

    private String specialCondition; //该类用于查询条件时，字段可作为查询条件
}