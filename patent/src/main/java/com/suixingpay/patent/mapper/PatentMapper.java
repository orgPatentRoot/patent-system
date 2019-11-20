package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.Patent;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PatentMapper {

    Patent selectByPrimaryKey(Integer patentId);

    List<Patent> selectAll();

    List<Patent> selectExamine();

    int auditPass(Integer id);

    int auditFailed(Integer id);
}