package com.suixingpay.patent.util;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.pojo.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Patent查询参数安全校验替换
     * @param patent
     * @return
     */
    public static void patentParamReplace(Patent patent) {
        String regEx = regEx = "[ %]"; //不允许输入空格和百分号，存在则将其替换成_防止SQL注入
        if (patent.getPatentName() != null) {
            patent.setPatentName(patent.getPatentName().replaceAll(" ", ""));
            patent.setPatentName(patent.getPatentName().replaceAll("%", " "));
        }
        if (patent.getPatentCaseNum() != null) {
            patent.setPatentCaseNum(patent.getPatentCaseNum().replaceAll(regEx, "❀"));
        }
        if (patent.getPatentApplyNum() != null) {
            patent.setPatentApplyNum(patent.getPatentApplyNum().replaceAll(regEx, "❀"));
        }
        if (patent.getPatentInventor() != null) {
            patent.setPatentInventor(patent.getPatentInventor().replaceAll(regEx, "❀"));
        }
        if (patent.getIndexContent() != null) {
            patent.setIndexContent(patent.getIndexContent().replaceAll(regEx, "❀"));
        }
    }

    //专利插入修改值特殊符号校验
    public static boolean patentParamCheck(Patent patent, String[] indexContent) {
        String regEx = regEx = "[ %]"; //不允许输入空格和百分号
        Pattern p = Pattern.compile(regEx);
        Matcher m;
        if (patent.getPatentBatch() != null) {
            m = p.matcher(patent.getPatentBatch());
            if(m.find()) {
                return true;
            }
        }
        if (patent.getPatentCaseNum() != null) {
            m = p.matcher(patent.getPatentCaseNum());
            if(m.find()) {
                return true;
            }
        }
        if (patent.getPatentApplyNum() != null) {
            m = p.matcher(patent.getPatentApplyNum());
            if(m.find()) {
                return true;
            }
        }
        if (patent.getPatentTechnicalContact() != null) {
            m = p.matcher(patent.getPatentTechnicalContact());
            if(m.find()) {
                return true;
            }
        }
        if (patent.getPatentApplyPerson() != null) {
            m = p.matcher(patent.getPatentApplyPerson());
            if(m.find()) {
                return true;
            }
        }
        if (patent.getPatentName() != null) {
            m = p.matcher(patent.getPatentName());
            if(m.find()) {
                return true;
            }
        }
        if (patent.getPatentType() != null) {
            m = p.matcher(patent.getPatentType());
            if(m.find()) {
                return true;
            }
        }
        if (patent.getPatentInventor() != null) {
            m = p.matcher(patent.getPatentInventor());
            if(m.find()) {
                return true;
            }
        }
        p = Pattern.compile(regEx);
        if (patent.getPatentRemarks() != null) {
            m = p.matcher(patent.getPatentRemarks());
            if(m.find()) {
                return true;
            }
        }
        p = Pattern.compile(regEx);
        for (String index : indexContent) {
            m = p.matcher(index);
            if(m.find()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 普通用户登录判断
     * @param message
     * @return
     */
    public static boolean userIsLogin(Message message, User user) {
        if (user == null || user.getUserId() == null) {
            message.setMessage(null, 401, "用户没有登录", false);
            return false;
        } else {
//            if (user.getUserId() == 1) {
//                message.setMessage(null, 401, "不是普通用户登录！", false);
//                return false;
//            }
        }
        return true;
    }

    /**
     * 管理员登录判断
     * @param message
     * @return
     */
    public static boolean userIsManagerLogin(Message message, User user) {
        if (user == null || user.getUserId() == null) {
            message.setMessage(null, 401, "用户没有登录", false);
            return false;
        } else {
//            if (user.getUserId() != 1) {
//                message.setMessage(null, 401, "不是管理员登录！", false);
//                return false;
//            }
        }
        return true;
    }

    /**
     * id判断是否为空
     * @param message
     * @return
     */
    public static boolean idIsEmpty(Message message, Integer id) {
        if (id == null) {
            message.setMessage(null, 400, "Id不能为空", false);
            return true;
        }
        return false;
    }

    /**
     * 状态ID不合法时，数据库查出的数据为空
     */
    public static boolean isStatusId(Message message, List list){
        if (list.size() == 0) {
            message.setMessage(null,400,"状态ID不合法",false);
            return true; 
        }
        return false;
    }
}
