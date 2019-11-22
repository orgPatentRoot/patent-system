package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.History;
import org.springframework.stereotype.Service;

@Service
public interface HistoryService {
    void insertHistory(History history);
}
