package com.suixingpay.patent.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class Status {
    private int statusId;//状态id
    private String statusName;//状态名称
}