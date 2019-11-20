package com.suixingpay.patent.pojo;

import java.util.Date;

public class History {
    private Integer historyId;

    private String historyPatent;

    private String historyModification;

    private String historyUser;

    private Date historyCreateTime;


    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public String getHistoryPatent() {
        return historyPatent;
    }

    public void setHistoryPatent(String historyPatent) {
        this.historyPatent = historyPatent == null ? null : historyPatent.trim();
    }

    public String getHistoryModification() {
        return historyModification;
    }

    public void setHistoryModification(String historyModification) {
        this.historyModification = historyModification == null ? null : historyModification.trim();
    }

    public String getHistoryUser() {
        return historyUser;
    }

    public void setHistoryUser(String historyUser) {
        this.historyUser = historyUser == null ? null : historyUser.trim();
    }

    public Date getHistoryCreateTime() {
        return historyCreateTime;
    }

    public void setHistoryCreateTime(Date historyCreateTime) {
        this.historyCreateTime = historyCreateTime;
    }
}