package com.suixingpay.patent.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 将时间格式统一化
 * @Author 朱金圣[zhu_js@suixingpay.com]
 * @Date 2019/12/2 20:39
 * @Version 1.0
 */
public class DateSetting {
    public static Date unifyDate(Date date) {
        if(date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String timeStr = sdf.format(date);
            try {
                date = sdf.parse(timeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
}
