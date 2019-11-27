package com.suixingpay.patent.controller;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "/status", produces = "application/json; charset=utf-8")
public class StatusController {

    @Autowired
    private StatusService statusService;

    /**
     * 查询所有的进度状态
     * @return
     */
    @RequestMapping("/queryAllStatus")
    @ResponseBody
    public Message queryAllStatus(HttpServletResponse response) {
        Message message = statusService.queryAllStatus();
        response.setStatus(message.getStatus());
        return message;
    }

    /**
     * 根据进度状态的id，查找对应状态的名称
     * @param statusId
     * @return
     */
    @RequestMapping("/selectStatusNameById")
    @ResponseBody
    public Message selectStatusNameById(Integer statusId, HttpServletResponse response) {
        Message message = new Message();
        if (statusId == null || statusId > 12 || statusId < -1 ) {
            message.setMessage(null, 400, "状态ID不合法", false);
            response.setStatus(message.getStatus());
            return message;
        }
        Message message1 = statusService.selectStatusNameById(statusId);
        response.setStatus(message1.getStatus());
        return message1;
    }

    /**
     * 根据进度状态的名称，查找对应状态的id
     * @param statusName
     * @return
     */
    @RequestMapping("/selectStatusIdByName")
    @ResponseBody
    public Message selectStatusIdByName(String statusName, HttpServletResponse response) {
        Message message = new Message();
        if (statusName == null) {
            message.setMessage(null, 400, "请输入要查询的状态名称", false);
            response.setStatus(message.getStatus());
            return message;
        }
        Message message1 = statusService.selectStatusIdByName(statusName);
        response.setStatus(message1.getStatus());
        return message1;
    }

    /**
     * 查询出数据维护阶段的进度状态
     * @return
     */
    @RequestMapping("/queryAllStatusAfter")
    @ResponseBody
    public Message queryAllStatusAfter(HttpServletResponse response) {
        Message message = statusService.queryAllStatusAfter();
        response.setStatus(message.getStatus());
        return message;
    }


}
