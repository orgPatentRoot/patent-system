package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Message;
import lombok.Value;


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

    /**
     * 根据配置文件读出审核前 除了新建专利（0）、发明初合（1）的所有状态
     * @return
     */

    Message selectBeforeByYml(Integer patentStatusId);

    /**
     * 根据配置文件读出审核后 所有的状态
     * @return
     */
    Message selectAfterByYml(Integer patentStatusId);

    /**
     * 管理员审核时查询配置文件中 审核结点之前除方案讨论（2）的所有状态
     */
    Message adminSelectBeforeByYml(Integer patentStatusId);

    /**
     * 管理员在专利池中查询除新建专利（0）外的所有状态
     */
    Message adminselectAllByYml(Integer patentStatusId);
}
