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
public class File {
    private int fileId;//交底书主键
    private int filePatentId;//专利id
    private String filePath;//文件路径
    private int fileStatus;//文件状态 0不启用 1启用
    private Date fileCreateTime;//创建时间
}