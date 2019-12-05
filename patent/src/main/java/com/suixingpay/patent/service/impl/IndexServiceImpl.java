package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.IndexMapper;
import com.suixingpay.patent.pojo.Index;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.service.IndexService;
import com.suixingpay.patent.util.ParamCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    /**
     * 管理员查询所有的指标
     * @return
     */
    @Override
    public Message queryAllIndex() {
        Message message = new Message();
        List<Index> list = indexMapper.queryAllIndex();
        message.setMessage(list, 200, "查询成功", true);
        return message;

    }

    /**
     * 根据专利id，查询此专利的指标（管理员、用户都可以调用）
     * @param patentId
     * @return
     */
    @Override
    public Message selectIndexByPatentId(Integer patentId) {
        Message message = new Message();
        List<Index> list = indexMapper.selectIndexByPatentId(patentId);
        if (list.size() == 0) {
            message.setMessage(list, 200, "该专利Id无指标！", false);
            return message;
        }
        message.setMessage(list, 200, "查询成功", true);
        return message;
    }

    /**
     * 用户插入一条指标
     * @param index
     * @return
     */
    @Override
    public Message insertIndexContent(Index index) {
        Message message = new Message();
        index.setIndexCreateTime(new Date());
        //内容特殊字符校验
        //指标只允许输入汉字、英文、数字、+*/=!@#$^&(),.;:'"！……{}_|“”，。《》<>~（）、-
        String regEx = "^[a-zA-Z0-9\u4E00-\u9FA5+*/=!@#$^&(),.;:'\"！……{}_|“”，。《》<>~（）、-]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(index.getIndexContent());
        if(!m.matches()) {
            message.setMessage(null, 200, "指标只允许输入汉字、英文、数字、+、-、*、/、=", true);
            return message;
        }
        if (indexMapper.insertIndexContent(index) != 0) {
            message.setMessage(null, 200, "指标添加成功", true);
            return message;
        } else {
            message.setMessage(null, 400, "指标添加失败", false);
            return message;
        }
    }

    /**
     * 用户修改指标内容（只能修改内容），根据指标ID匹配
     *
     * @param index
     * @return
     */
    @Override
    public Message updateIndexContent(Index index) {
        Message message = new Message();
        if (indexMapper.updateIndexContent(index) == 0) {
            message.setMessage(null, 400, "指标更新失败", false);
            return message;
        } else {
            message.setMessage(null, 200, "指标更新成功", true);
            return message;
        }
    }

    /**
     * 用户删除一条指标
     * @param indexId
     * @return
     */
    @Override
    public Message deleteIndex(Integer indexId) {
        Message message = new Message();
        if (indexMapper.deleteIndex(indexId) != 0) {
            message.setMessage(null, 200, "指标删除成功", true);
            return message;
        } else {
            message.setMessage(null, 400, "指标删除失败", false);
            return message;
        }


    }
}

