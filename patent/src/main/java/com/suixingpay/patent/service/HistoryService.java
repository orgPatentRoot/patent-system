package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.History;
import com.suixingpay.patent.pojo.Message;
import org.springframework.stereotype.Service;

@Service
public interface HistoryService {

    /**
     * 利用AOP+注解记录专利的流程历史
     * @param history
     */
    void insertHistory(History history);

    /**
     * 全查数据库中的流程记录
     * @return
     */
    Message queryHistory(History history);

    /**
     * 根据专利号查询专利的操作历史
     * @param patentId
     * @return
     */
    Message selectHistoryByPatent(Integer patentId);
}
