package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Patent;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface PatentService {
    Patent getById(Integer id);

    List<Patent> selectAll();

    List<Patent> selectExamine();

    int auditPass(Integer id);

    int auditFailed(Integer id);
}
