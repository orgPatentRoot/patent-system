package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.History;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
public interface HistoryMapper {

    void insertHistory(History history);
}