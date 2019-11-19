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
public class History {
    private int historyId;//流程历史id
    private String historyPatent;//流程历史专利
    private String historyModification;//修改项
    private String historyUser;//修改人
    private Date historyCreateTime;//操作时间
}