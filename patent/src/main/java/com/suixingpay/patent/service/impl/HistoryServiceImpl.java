package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.HistoryMapper;
import com.suixingpay.patent.pojo.History;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryMapper historyMapper;

    /**
     * 利用AOP+注解记录专利的流程历史
     * @param history
     */
    @Override
    public void insertHistory(History history) {
        historyMapper.insertHistory(history);
    }

    /**
     * 全查数据库中的流程记录
     * @return
     */
    @Override
    public Message queryHistory() {
        Message message = new Message();
        List<History> list = historyMapper.queryHistory();
        message.setMessage(list,200,"查询成功",true);
        return message;
    }

    /**
     * 根据专利号查询专利的操作历史
     * @param patentId
     * @return
     */
    @Override
    public Message selectHistoryByPatent(Integer patentId) {
        Message message = new Message();
        List<History> list = historyMapper.selectHistoryByPatent(patentId);
        if (list.size() == 0) {
            message.setMessage(list,200,"查询成功",true);
            return message;
        }
        message.setMessage(list,200,"查询成功",true);
        return message;
    }
}
