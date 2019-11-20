package com.suixingpay.patent.controller;

import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.service.PatentService;
import com.suixingpay.patent.service.impl.PatentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/patent")
public class PatentController {

    @Autowired
    private PatentServiceImpl patentService;

    @RequestMapping("/insertPatent")
    public String insertPatentController(HttpServletRequest request){
        String patentBatch = "第一批";//专利批次
        String patentCaseNum = "JLE19I0485E";//案例文号
        String patentApplyNum = "2019105719165";//申请号
        String str = "2019-6-28";//申请时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date patentApplyTime = date;
        String patentTechnicalContact = "刘一男";//技术联系人
        String patentApplyPerson = "北京银企融合技术开发有限公司";//申请人
        int patentCreatePerson = 1;//创建人
        String patentName = "一种任务响应时效评估方法、调度方法、设备和存储介质";//专利名
        int patentSign = 1;//审核状态，1表示审核中
        int patentStatusId = 0;//专利进度,0表示新建状态
        String patentType = "发明";//发明类型
        String patentInventor = "于振坤、徐华";//发明人
        int patentWriter = 2;//撰写人
        String patentRemarks = "检索到的现有技术是他们之前自己公开的，决定撤销该案。";//备注

//        if()

        //添加数据进入实体类
        Patent patent = new Patent();
        patent.setPatentBatch(patentBatch);
        patent.setPatentCaseNum(patentCaseNum);
        patent.setPatentApplyNum(patentApplyNum);
        patent.setPatentApplyTime(patentApplyTime);
        patent.setPatentTechnicalContact(patentTechnicalContact);
        patent.setPatentApplyPerson(patentApplyPerson);
        patent.setPatentCreatePerson(patentCreatePerson);
        patent.setPatentName(patentName);
        patent.setPatentSign(patentSign);
        patent.setPatentStatusId(patentStatusId);
        patent.setPatentType(patentType);
        patent.setPatentInventor(patentInventor);
        patent.setPatentWriter(patentWriter);
        patent.setPatentRemarks(patentRemarks);
        patentService.insertPatentSevice(patent);
        return "插入成功";
    }
}
