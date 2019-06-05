package com.maxzuo.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * JDK8 新增日期时间API
 * Created by zfh on 2019/02/22
 */
class DateTimeTest {

    @DisplayName("获取日期时间的信息")
    @Test
    void testTime() {
        // LocalDate类获取日期信息。格式为 2018-09-06
        LocalDate nowDate = LocalDate.now();
        System.out.println("nowDate: " + nowDate);

        /// 日期格式化
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("newDateStr: " + nowDate.format(dtf));

        // LocalTime类获取时间信息。格式为 15:33:56.749
        LocalTime nowTime = LocalTime.now();
        System.out.println("nowTime: " + nowTime);

        // LocalDateTime类获取日期时间信息。格式为 2018-09-06T15:33:56.750
        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println("nowDateTime: " + nowDateTime);
    }

    @DisplayName("时间戳")
    @Test
    void testTimestamp() {
        // 输出格式：2019-02-22T02:07:32.398Z
        System.out.println("当前时间戳：" + Instant.now());

        // 类型转换
        // 输出格式：Fri Feb 22 10:07:32 CST 2019
        Date date = Date.from(Instant.now());
        System.out.println("当前时间戳：" + date);
    }

    @DisplayName("日期时间格式化")
    @Test
    void testDateTimeFormat() {
        /*
            阿里规约：SimpleDateFormat 是线程不安全的类，一般不要定义为 static 变量，如果定义为static，必须加锁，或者使用 DateUtils 工具类。
            说明：如果是 JDK8 的应用，可以使用 Instant 代替 Date， LocalDateTime 代替 Calendar，DateTimeFormatter 代替 SimpleDateFormat，
                  官方给出的解释： simple beautiful strong immutable thread-safe。
         */
        LocalDateTime nowDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String format = nowDateTime.format(dtf);
        // 输出：2019-02-22 10:10:00.263
        System.out.println("format: " + format);

        // 正反都能调
        // 输出：2019-02-22 10:10:00.263
        String format1 = dtf.format(nowDateTime);
        System.out.println("format1: " + format1);

        /// 格式化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format2 = nowDateTime.format(dateTimeFormatter);
        System.out.println(format2);
    }

    @DisplayName("字符串解析为日期")
    @Test
    public void testStringParseToDateTime () {
        /// 格式化字符串日期
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateStr = LocalDate.parse("2019-06-06", yyyyMMdd);
        System.out.println("localDateStr: " + localDateStr);

        /// 解析日期+时间
        DateTimeFormatter yyyyMMddHHmm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTimeStr = LocalDateTime.parse("2019-06-05 14:02", yyyyMMddHHmm);
        System.out.println("localDateTimeStr: " + localDateTimeStr);
    }
}
