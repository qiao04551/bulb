package com.maxzuo.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * Apache DateUtils的使用
 * Created by zfh on 2019/01/05
 */
class DateUtilsTest {

    /**
     * 解析日期时间字符串
     */
    @Test
    void testParseDate() throws ParseException {
        // 解析时间
        Date date = DateUtils.parseDate("2019-1-5 8:30:20:222", "yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println(date);

        Date date1 = DateUtils.parseDate("2019-11-25", "yyyy-MM-dd");
        System.out.println(date1);
    }

    /**
     * 日期格式化
     */
    @Test
    void testDateFormat () {
        // 格式化时间
        String formatTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("formatTime: " + formatTime);

        String dateStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        System.out.println("dateStr: " + dateStr);

        String timeStr = DateFormatUtils.format(new Date(), "HH:mm");
        System.out.println("timeStr: " + timeStr);
    }

    /**
     * 对日期的加减
     */
    @Test
    void testCaculateDate() {
        Date date = DateUtils.addYears(new Date(), 1);
        DateUtils.addMonths(new Date(), 1);
        DateUtils.addDays(new Date(), 1);
        DateUtils.addHours(new Date(), 1);
        DateUtils.addMinutes(new Date(), 1);
        DateUtils.addSeconds(new Date(), 1);
        // 判断两个日期是否是同一天
        boolean aBoolean = DateUtils.isSameDay(new Date(), new Date());
        // 格式化时间
        String formatTime = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("formatTime: " + formatTime);
    }
}
