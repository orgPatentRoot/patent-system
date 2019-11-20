package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.History;

public interface HistoryMapper {
    int insert(History record);

    int insertSelective(History record);
}