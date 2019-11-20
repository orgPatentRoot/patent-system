package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Patent;
import org.springframework.stereotype.Service;

@Service
public interface PatentService {
    public int insertPatentSevice(Patent patent);
}
