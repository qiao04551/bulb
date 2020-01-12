package com.maxzuo.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * Created by zfh on 2019/01/31
 */
class RegularTest {

    @DisplayName("正则语法")
    @Test
    void testRegex() {
        // 使用String类的matches方法正则匹配
        String str = "863329112@qq.com";
        String regex = "\\d+@(qq.com|gmail.com)";
        System.out.println(str.matches(regex));

        // 使用Pattern和Matcher类
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("8633299112@qq.com");
        System.out.println("matcher: " + matcher.matches());
    }

    @DisplayName("匹配日志")
    @Test
    void testRegexLog() {
        String record = "2019-01-31 11:45:42,305 ERROR [com.zxcity.ws.cloudShop.ScShopActivePosterRest] - 参数解析错误";

        String dateStr = "2019-01-31 11:45:42,305";
        String dateRegex = "(\\d|-)+\\s(\\d|:|,)+";
        System.out.println(dateStr.matches(dateRegex));

        String levelStr = "ERROR";
        String levelRegex = "[A-Z]+";
        System.out.println(levelStr.matches(levelRegex));

        String className = "[com.zxcity.ws.cloudShop.ScShopActivePosterRest]";
        String classRegex = "^\\[\\S+]$";
        System.out.println(className.matches(classRegex));

        String errMessage = "参数解析错误";
        String msgRegex = "^*[\u4E00-\u9FA5]+$";
        System.out.println(errMessage.matches(msgRegex));
    }

    @Test
    void testTemp() {
        String msg = "2019-02-01 16:14:10,005 [RMI TCP Connection(127.0.01:2181)[Catalina]] INFO [RMI TCP Connection(127.0.01:2181)[Catalina]] - 异常信息 \\njava.lang.RuntimeException: 异常信息\\n\\tat com.maxzuo.MainTest.testLog(MainTest.java:23)";
        String regex = "(\\d|-)+\\s(\\d|:|,)+" + " " + "\\[.+]" + " " + "[A-Z\\s]+" + " " + "\\[.+]" + " - " + ".+";
        System.out.println(msg.matches(regex));

        String msg2 = "2019-02-11 14:11:33,714 [main] DEBUG com.maxzuo.MainTest (MainTest.java:18) - hello world";
        String regex2 = "(\\d|-)+\\s(\\d|:|,)+" + " " + "\\[\\S+]" + " " + "[A-Z\\s]+" + " " + "[a-zA-Z0-9.]+" + " "
                        + ".+";
        System.out.println("msg2: " + msg2.matches(regex2));
    }

    @DisplayName("提取指定的字符串")
    @Test
    void extractString() {
        String message = "ERROR - ScSysUserYxstautsController.mockClient$original$EYDFbfPe(260) | traceId=10000.88.15553224020090762 Stream closed traceId=30000.88.15553224020090763";
        String regex = "traceId=([.\\d]*)";
        String subString = getSubUtilSimple(message, regex);
        System.out.println(subString);
    }

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个
     */
    private String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    @Test
    void testSplit() {
        String str = "hello.world";
        String[] split = str.split("\\.");  // . 是转义字符，需要转义
        System.out.println(Arrays.toString(split));

        System.out.println(str.indexOf("."));   // 不需要转义
    }
}
