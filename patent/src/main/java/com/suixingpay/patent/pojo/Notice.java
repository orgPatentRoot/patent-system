package com.suixingpay.patent.pojo;

import java.util.Date;

public class Notice {
    private Integer noticeId;

    private Integer noticePatentId;

    private String noticePath;

    private String noticeName;

    private Integer noticeStatus;

    private Date noticeCreateTime;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getNoticePatentId() {
        return noticePatentId;
    }

    public void setNoticePatentId(Integer noticePatentId) {
        this.noticePatentId = noticePatentId;
    }

    public String getNoticePath() {
        return noticePath;
    }

    public void setNoticePath(String noticePath) {
        this.noticePath = noticePath == null ? null : noticePath.trim();
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName == null ? null : noticeName.trim();
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Date getNoticeCreateTime() {
        return noticeCreateTime;
    }

    public void setNoticeCreateTime(Date noticeCreateTime) {
        this.noticeCreateTime = noticeCreateTime;
    }
}