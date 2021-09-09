package com.dingtalk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * time 工具类
 */
public class TimeUtil {

    private static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 字符串date转时间戳
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static long strToLong(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        Date date = formatter.parse(dateStr);
        return date.getTime();
    }

    /**
     * date转字符串
     * @param date
     * @return
     * @throws ParseException
     */
    public static String dateToStr(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return formatter.format(date);
    }
}
