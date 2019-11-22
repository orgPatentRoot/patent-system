package com.suixingpay.patent.pojo;


import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class Patent {
    private Integer patentId;

    private String patentBatch;

    private String patentCaseNum;

    private String patentApplyNum;

    private Date patentApplyTime;

    private String patentTechnicalContact;

    private String patentApplyPerson;

    private Integer patentCreatePerson;

    private String patentName;

    private Integer patentSign;

    private Integer patentStatusId;

    private String patentType;

    private String patentInventor;

    private Integer patentWriter;

    private String patentRemarks;

    public Integer getPatentId() {
        return patentId;
    }

    public void setPatentId(Integer patentId) {
        this.patentId = patentId;
    }

    public String getPatentBatch() {
        return patentBatch;
    }

    public void setPatentBatch(String patentBatch) {
        this.patentBatch = patentBatch == null ? null : patentBatch.trim();
    }

    public String getPatentCaseNum() {
        return patentCaseNum;
    }

    public void setPatentCaseNum(String patentCaseNum) {
        this.patentCaseNum = patentCaseNum == null ? null : patentCaseNum.trim();
    }

    public String getPatentApplyNum() {
        return patentApplyNum;
    }

    public void setPatentApplyNum(String patentApplyNum) {
        this.patentApplyNum = patentApplyNum == null ? null : patentApplyNum.trim();
    }

    public Date getPatentApplyTime() {
        return patentApplyTime;
    }

    public void setPatentApplyTime(Date patentApplyTime) {
        this.patentApplyTime = patentApplyTime;
    }

    public String getPatentTechnicalContact() {
        return patentTechnicalContact;
    }

    public void setPatentTechnicalContact(String patentTechnicalContact) {
        this.patentTechnicalContact = patentTechnicalContact == null ? null : patentTechnicalContact.trim();
    }

    public String getPatentApplyPerson() {
        return patentApplyPerson;
    }

    public void setPatentApplyPerson(String patentApplyPerson) {
        this.patentApplyPerson = patentApplyPerson == null ? null : patentApplyPerson.trim();
    }

    public Integer getPatentCreatePerson() {
        return patentCreatePerson;
    }

    public void setPatentCreatePerson(Integer patentCreatePerson) {
        this.patentCreatePerson = patentCreatePerson;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName == null ? null : patentName.trim();
    }

    public Integer getPatentSign() {
        return patentSign;
    }

    public void setPatentSign(Integer patentSign) {
        this.patentSign = patentSign;
    }

    public Integer getPatentStatusId() {
        return patentStatusId;
    }

    public void setPatentStatusId(Integer patentStatusId) {
        this.patentStatusId = patentStatusId;
    }

    public String getPatentType() {
        return patentType;
    }

    public void setPatentType(String patentType) {
        this.patentType = patentType == null ? null : patentType.trim();
    }

    public String getPatentInventor() {
        return patentInventor;
    }

    public void setPatentInventor(String patentInventor) {
        this.patentInventor = patentInventor == null ? null : patentInventor.trim();
    }

    public Integer getPatentWriter() {
        return patentWriter;
    }

    public void setPatentWriter(Integer patentWriter) {
        this.patentWriter = patentWriter;
    }

    public String getPatentRemarks() {
        return patentRemarks;
    }

    public void setPatentRemarks(String patentRemarks) {
        this.patentRemarks = patentRemarks == null ? null : patentRemarks.trim();
    }

    @Override
    public String toString() {
        return "Patent{" +
                "patentId=" + patentId +
                ", patentBatch='" + patentBatch + '\'' +
                ", patentCaseNum='" + patentCaseNum + '\'' +
                ", patentApplyNum='" + patentApplyNum + '\'' +
                ", patentApplyTime=" + patentApplyTime +
                ", patentTechnicalContact='" + patentTechnicalContact + '\'' +
                ", patentApplyPerson='" + patentApplyPerson + '\'' +
                ", patentCreatePerson=" + patentCreatePerson +
                ", patentName='" + patentName + '\'' +
                ", patentSign=" + patentSign +
                ", patentStatusId=" + patentStatusId +
                ", patentType='" + patentType + '\'' +
                ", patentInventor='" + patentInventor + '\'' +
                ", patentWriter=" + patentWriter +
                ", patentRemarks='" + patentRemarks + '\'' +
                '}';
    }
}