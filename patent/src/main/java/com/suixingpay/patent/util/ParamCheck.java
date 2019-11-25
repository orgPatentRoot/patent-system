package com.suixingpay.patent.util;

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
    public static void pageParamCheck(Integer page) {
        if(page == null || page < 1)
            throw new RuntimeException("页数不合法");
    }
}
