package com.suixingpay.patent.util;

import com.suixingpay.patent.pojo.Patent;

import javax.servlet.http.HttpServletRequest;

public class ParamCheck {
    public static boolean paramCheck(String... inputs) {
        for (String input : inputs) {
            if (input == null || "".equals(input)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 页数检查
     * @param page
     * @return
     */
    public static boolean pageParamCheck(Integer page) {
        if (page == null || page < 1) {
            return false;
        }
        return true;
    }

    /**
     * Patent参数安全校验替换
     * @param patent
     * @return
     */
    public static void patentParamReplace(Patent patent) {
        if (patent.getPatentName() != null) {
            patent.setPatentName(patent.getPatentName().replaceAll("[${}'%=]", " "));
        }
        if (patent.getPatentCaseNum() != null) {
            patent.setPatentCaseNum(patent.getPatentCaseNum().replaceAll("[${}'%=]", " "));
        }
        if (patent.getPatentApplyNum() != null) {
            patent.setPatentApplyNum(patent.getPatentApplyNum().replaceAll("[${}'%=]", " "));
        }
        if (patent.getPatentInventor() != null) {
            patent.setPatentInventor(patent.getPatentInventor().replaceAll("[${}'%=]", " "));
        }
        if (patent.getIndexContent() != null) {
            patent.setIndexContent(patent.getIndexContent().replaceAll("[${}'%=]", " "));
        }
    }
}
