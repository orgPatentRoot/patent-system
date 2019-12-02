package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Message;


public interface StatusService {

    /**
     * 查询所有的进度状态
     * @return
     */
    Message queryAllStatus();

    /**
     * 根据进度状态的id，查找对应状态的名称
     * @param statusId
     * @return
     */
    Message selectStatusNameById(Integer statusId);

    /**
     * 根据进度状态的名称，查找对应状态的id
     * @param statusName
     * @return
     */
    Message selectStatusIdByName(String statusName);

    /**
     * 查询出数据维护阶段的进度状态
     * @return
     */
    Message queryAllStatusAfter();
}
