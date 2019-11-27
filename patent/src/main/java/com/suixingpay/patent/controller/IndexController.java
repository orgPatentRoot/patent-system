package com.suixingpay.patent.controller;

import com.suixingpay.patent.annotation.UserLog;
import com.suixingpay.patent.pojo.Index;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/index", produces = "application/json; charset=utf-8")
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 管理员查询所有的指标
     * @return
     */
    @RequestMapping("/queryAllIndex")
    @ResponseBody
    public Message queryAllIndex(HttpServletResponse response) {
        Message message = indexService.queryAllIndex();
        response.setStatus(message.getStatus());
        return message;
    }

    /**
     * 根据专利id，查询此专利的指标（管理员、用户都可以调用）
     * @param patentId
     * @return
     */
    @RequestMapping("/selectIndexByPatentId")
    @ResponseBody
    public Message selectIndexByPatentId(Integer patentId, HttpServletResponse response) {
        Message message = new Message();
        if (patentId == null) {
            message.setMessage(null, 400, "请输入查询指标所属的专利ID", true);
            response.setStatus(message.getStatus());
            return message;
        }
        Message message1 = indexService.selectIndexByPatentId(patentId);
        response.setStatus(message1.getStatus());
        return message1;
    }

    /**
     * 用户插入一条指标
     * @param index
     * @return
     */
//    @UserLog("插入指标！")
    @PostMapping(value = "/insertIndexContent")
    @ResponseBody
    public Message insertIndexContent(@RequestBody @Valid Index index, HttpServletResponse response) {
        System.out.println(111);
        Message message = indexService.insertIndexContent(index);
        response.setStatus(message.getStatus());
        return message;
    }

    /**
     * 用户删除一条指标
     * @param indexId
     * @return
     */
    @UserLog("删除指标！")
    @RequestMapping("/deleteIndex")
    @ResponseBody
    public Message deleteIndex(Integer indexId, HttpServletResponse response) {
        Message message = new Message();
        if (indexId == null) {
            message.setMessage(null, 400, "请输入被删除指标的ID", true);
            response.setStatus(message.getStatus());
            return message;
        }
        Message message1 = indexService.deleteIndex(indexId);
        response.setStatus(message1.getStatus());
        return message1;
    }

    /**
     * 用户修改指标内容（只能修改内容），根据指标ID匹配
     *
     * @param index
     * @return
     */
    @PostMapping("/updateIndexContent")
    @ResponseBody
    public Message updateIndexContent(@RequestBody Index index, HttpServletResponse response) {
        Message message = new Message();
        if (index.getIndexContent() == null  || index.getIndexContent().equals("") || index.getIndexId() == null) {
            message.setMessage(null, 400, "请输入被删除指标的ID并且内容不能为空", true);
            response.setStatus(message.getStatus());
            return message;
        }
        Message message1 = indexService.updateIndexContent(index);
        response.setStatus(message1.getStatus());
        return message1;
    }

}