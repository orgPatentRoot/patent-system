package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.StatusMapper;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Status;
import com.suixingpay.patent.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusMapper statusMapper;


    /**
     * 查询所有的进度状态
     * @return
     */
    @Override
    public Message queryAllStatus() {
        Message message = new Message();
        List<Status> list = statusMapper.queryAllStatus();
        message.setMessage(list, 200, "查询成功", true);
        return  message;
    }

    /**
     * 根据进度状态的id，查找对应状态的名称
     * @param statusId
     * @return
     */
    @Override
    public Message selectStatusNameById(Integer statusId) {
        Message message = new Message();
        Status statusName = statusMapper.selectStatusNameById(statusId);
        message.setMessage(statusName, 200, "查询成功", true);
        return message;
    }

    /**
     * 根据进度状态的名称，查找对应状态的id
     * @param statusName
     * @return
     */
    @Override
    public Message selectStatusIdByName(String statusName) {
        Message message = new Message();
        Status statusId = statusMapper.selectStatusIdByName(statusName);
        if (statusId == null) {
            message.setMessage(statusId, 400, "状态名称不合法", false);
            return message;
        }
        message.setMessage(statusId, 200, "查询成功", true);
        return message;
    }

    /**
     * 查询出数据维护阶段的进度状态
     * @return
     */
    @Override
    public Message queryAllStatusAfter() {
        Message message = new Message();
        List<Status> list = statusMapper.queryAllStatusAfter();
        message.setMessage(list, 200, "查询成功", true);
        return message;
    }

    /**
     * 根据配置文件读出审核前 除了新建专利（0）、发明初合（1）的所有状态
     * @param patentStatusId
     * @return
     */
    @Override
    public Message selectBeforeByYml(Integer patentStatusId) {
        Message message = new Message();
        /*if (patentNeedCheckStatus == null) {
            message.setMessage(null, 400, "查询失败", true);
            return message;
        }*/

        List<Status> list = statusMapper.selectBeforeByYml(patentStatusId);
        message.setMessage(list, 200, "查询成功", true);
        return message;
    }


    /**
     * 根据配置文件读出审核后 所有的状态
     * @param patentStatusId
     * @return
     */
    @Override
    public Message selectAfterByYml(Integer patentStatusId) {
        Message message = new Message();
        List<Status> list = statusMapper.selectAfterByYml(patentStatusId);
        message.setMessage(list, 200, "查询成功", true);
        return message;
    }

    @Override
    public Message adminSelectBeforeByYml(Integer patentStatusId) {
        Message message = new Message();
        List<Status> list = statusMapper.adminSelectBeforeByYml(patentStatusId);
        message.setMessage(list, 200, "查询成功", true);
        return message;
    }

    @Override
    public Message adminselectAllByYml(Integer patentStatusId) {
        Message message = new Message();
        List<Status> list = statusMapper.adminselectAllByYml(patentStatusId);
        message.setMessage(list, 200, "查询成功", true);
        return message;
    }


}
