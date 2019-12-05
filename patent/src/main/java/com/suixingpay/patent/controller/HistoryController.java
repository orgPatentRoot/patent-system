package com.suixingpay.patent.controller;

import com.suixingpay.patent.pojo.History;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/history", produces = "application/json; charset=utf-8")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    /**
     * 全查数据库中的流程记录
     * @return
     */
    @RequestMapping("/queryHistory")
    public Message queryHistory(@RequestBody History history) {
        return historyService.queryHistory(history);
    }

    /**
     * 根据专利号查询专利的操作历史
     * @param patentId
     * @return
     */
    @GetMapping("/selectHistoryByPatent")
    public Message selectHistoryByPatent(Integer patentId) {
        Message message = new Message();
        if (patentId == null) {
            message.setMessage(null, 400, "专利ID为空", false);
            return message;
        }
        return historyService.selectHistoryByPatent(patentId);
    }
}
