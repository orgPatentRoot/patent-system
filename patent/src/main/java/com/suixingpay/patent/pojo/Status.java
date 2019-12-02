package com.suixingpay.patent.pojo;

import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
@Component
public class Status {
    //@NotNull(message = "错误！进度状态ID为空")
    private Integer statusId;
   // @NotNull(message = "错误！进度状态名称为空")
    private String statusName;

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }
}