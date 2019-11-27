package com.suixingpay.patent.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Index {
    //@NotNull(message = "指标ID不能为空")
    private Integer indexId;
    @NotNull(message = "专利ID不能为空")
    private Integer indexPatentId;
    @NotNull(message = "指标内容不能为空")
    private String indexContent;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date indexCreateTime;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public Integer getIndexPatentId() {
        return indexPatentId;
    }

    public void setIndexPatentId(Integer indexPatentId) {
        this.indexPatentId = indexPatentId;
    }

    public String getIndexContent() {
        return indexContent;
    }

    public void setIndexContent(String indexContent) {
        this.indexContent = indexContent == null ? null : indexContent.trim();
    }

    public Date getIndexCreateTime() {
        return indexCreateTime;
    }

    public void setIndexCreateTime(Date indexCreateTime) {
        this.indexCreateTime = indexCreateTime;
    }
}