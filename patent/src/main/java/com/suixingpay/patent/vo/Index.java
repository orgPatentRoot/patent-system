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
public class Index {
    private int indexId;//指标id
    private int indexPatentId;//专利id
    private String indexContent;//指标内容
    private Date indexCreateDate;//指标创建时间
}