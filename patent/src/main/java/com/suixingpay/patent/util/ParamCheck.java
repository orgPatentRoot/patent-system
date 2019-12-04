package com.suixingpay.patent.util;

import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.pojo.User;

import java.util.List;

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
