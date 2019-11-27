package com.suixingpay.patent.service;

import com.suixingpay.patent.pojo.Index;
import com.suixingpay.patent.pojo.Message;




public interface IndexService {

    /*
     * 管理员查询所有的指标
     * */
    Message queryAllIndex();

    /*
     * 根据专利id，查询此专利的指标（管理员、用户都可以调用）
     * */
    Message selectIndexByPatentId(Integer patentId);

    /*
     * 用户插入一条指标
     * */
    Message insertIndexContent(Index index);

    /*
     * 用户修改指标内容（只能修改内容）
     * */
    Message updateIndexContent(Index inedx);

    /*
     * 用户删除一条指标
     * */
    Message deleteIndex(Integer indexId);
}