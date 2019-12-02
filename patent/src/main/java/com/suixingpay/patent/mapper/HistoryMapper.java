package com.suixingpay.patent.mapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suixingpay.patent.pojo.History;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HistoryMapper {

    /**
     * 利用AOP+注解记录专利的流程历史
     * @param history
     */
    void insertHistory(History history);

    /**
     * 全查数据库中的流程记录
     * @return
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    List<History> queryHistory();

    /**
     * 根据专利号查询专利的操作历史
     * @param patentId
     * @return
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    List<History> selectHistoryByPatent(Integer patentId);
}