package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.HistoryMapper;
import com.suixingpay.patent.pojo.History;
import com.suixingpay.patent.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryMapper historyMapper;

    @Override
    public void insertHistory(History history) {
        historyMapper.insertHistory(history);
    }
}
