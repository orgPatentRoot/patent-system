package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.PatentMapper;
import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PatentServiceImpl implements PatentService {
    @Resource
    private PatentMapper patentMapper;

    @Override
    public int insertPatentSevice(Patent patent) {
        return patentMapper.insert(patent);
    }
}
