package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.PatentMapper;
import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PatentServiceImpl implements PatentService {
    @Autowired(required = false)
    private PatentMapper patentMapper;
    @Override
    public Patent getById(Integer id) {
        return patentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Patent> selectAll() {
        return patentMapper.selectAll();
    }


    @Override
    public List<Patent> selectExamine() {
        return patentMapper.selectExamine();
    }

    @Override
    public int auditPass(Integer id) {
        return patentMapper.auditPass(id);
    }

    @Override
    public int insertPatentSevice(Patent patent) {
//        return patentMapper.insert(patent);
        return 0;
    }

    @Override
    public int auditFailed(Integer id) {
        return patentMapper.auditFailed(id);
    }
}
