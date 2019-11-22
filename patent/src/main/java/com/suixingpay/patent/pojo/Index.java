package com.suixingpay.patent.pojo;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class Index {
    private Integer indexId;

    private Integer indexPatentId;

    private String indexContent;

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