package com.maxzuo.socket;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * InetAddress 用于标识网络上的硬件资源，主要是IP地址
 * Created by zfh on 2019/01/24
 */
class InetAddressTest {

    @Test
    void testHostInfo() throws UnknownHostException {
        // 获取本地主机
        InetAddress host = InetAddress.getLocalHost();
        byte[] address = host.getAddress();
        System.out.println("原始IP地址: " + Arrays.toString(address));
        String[] strings = new String[4];
        // 在Java中byte类型的取值范围是-128〜127。如果返回的IP地址的某个字节是大于127的整数，在byte数组中就是负数。由于Java中没有无符号byte类型，因此，要想显示正常的IP地址，必须使用int或long类型。
        for (int i = 0; i < address.length; i++) {
            int ipSegment = address[i];
            strings[i] = String.valueOf((ipSegment < 0) ? ipSegment + 256 : ipSegment);
        }
        System.out.println("转换后IP地址：" + String.join(".", strings));

        System.out.println("IP地址的主机名: " + host.getHostName());
        System.out.println("IP地址字符串形式：" + host.getHostAddress());
        System.out.println("toString形式: " + host.toString());

        // 在给定主机名的情况下确定主机的 IP 地址。
        String remoteHostName = "www.baidu.com";
        InetAddress remoteHost = InetAddress.getByName(remoteHostName);
        System.out.println("remoteHostIp: " + remoteHost.getHostAddress());
    }
}
