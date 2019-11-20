package com.suixingpay.patent.controller;

import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patent")
public class PatentController {
    @Autowired
    private PatentService patentService;
    //根据Id查找所有专利信息
    @RequestMapping("/findPatent/{id}")
    public Patent getPatentById(@PathVariable("id") int id){
        return patentService.getById(id);
    }

    //显示所有专利信息
    @RequestMapping("/selectAll")
    public List<Patent> selectAll(Model model){
        List<Patent> patents = patentService.selectAll();
        //model.addAttribute("patents",patents);//查找到的patent列表放入model中前端可以调用。
        return patents;

        //return patentService.selectAll().toString();
    }

    //查找所有待审核的专利信息
    @RequestMapping("/selectExamine")
    public List<Patent> selectExamine(Model model){
        List<Patent> examines= patentService.selectExamine();
        return examines;

    }
    //通过审核
    @RequestMapping("/auditPass/{id}")
    public String updateSign(@PathVariable("id") Integer id){
        patentService.auditPass(id);
        return "审核通过";
    }

    //审核不通过
    @RequestMapping("/auditFailed/{id}")
    public String auditFailed(@PathVariable("id")Integer id){
        patentService.auditFailed(id);
        return "审核不通过";
    }
}
