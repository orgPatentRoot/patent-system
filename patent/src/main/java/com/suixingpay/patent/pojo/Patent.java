package com.suixingpay.patent.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class Patent {

    @NotNull(message = "专利ID不能为空！")
    private Integer patentId;//专利Id

    @NotNull(message = "批数不能为空！")
    private String patentBatch;//批数

    @NotNull(message = "案例文号不能为空")
    private String patentCaseNum;//案例文号

    @NotNull(message = "申请号不能为空")
    private String patentApplyNum;//申请号

    @NotNull(message = "申请时间不能为空")
    private Date patentApplyTime;//申请时间

    @NotNull(message = "技术联系人不能为空")
    private String patentTechnicalContact;//技术联系人中文名称

    @NotNull(message = "申请人不能为空")
    private String patentApplyPerson;//申请人中文名称

    private Integer patentCreatePerson;//创建人Id

    @NotNull(message = "专利名称不能为空")
    private String patentName;//专利中文名称

    private Integer patentSign;//专利审核状态

    private Integer patentStatusId;//专利进度状态

    @NotNull(message = "发明类型不能为空")
    private String patentType;//发明类型

    @NotNull(message = "发明人不能为空")
    private String patentInventor;//发明人中文名称

    private Integer patentWriter;//撰写人Id

    @NotNull(message = "备注不能为空")
    private String patentRemarks;

    private String statusName;//进度名称

    private String createPersonName;//创建人名字

    private String writerName;//撰写人名字

    private Integer currentPage;//当前页数

    private Integer pageCount;//总页数

    private String specialCondition;//该类用于查询条件时，字段可作为查询条件
}