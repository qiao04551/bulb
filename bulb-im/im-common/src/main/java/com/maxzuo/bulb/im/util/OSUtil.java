package com.maxzuo.bulb.im.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * 系统信息
 * <p>
 * Created by zfh on 2019/09/21
 */
public class OSUtil {

    private static volatile String OS_NAME;

    private static volatile String HOST_NAME;

    private static volatile List<String> IPV4_LIST;

    private static volatile int PROCESS_NO = 0;

    private static volatile int VAILABLE_PROCESSORS = 0;


    /**
     * Java虚拟机可用的处理器数量
     */
    public static int getNumberOfProcessors() {
        if (VAILABLE_PROCESSORS == 0) {
            VAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
        }
        return VAILABLE_PROCESSORS;
    }

    /**
     * 获取当前JVM进程PID
     */
    public static int getProcessNo () {
        if (PROCESS_NO == 0) {
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            PROCESS_NO = Integer.valueOf(runtimeMXBean.getName().split("@")[0]);
        }
        return PROCESS_NO;
    }

    /**
     * 操作系统名称
     */
    public static String getOsName() {
        if (OS_NAME == null) {
            OS_NAME = System.getProperty("os.name");
        }
        return OS_NAME;
    }

    /**
     * 主机名
     */
    public static String getHostName() {
        if (HOST_NAME == null) {
            try {
                InetAddress host = InetAddress.getLocalHost();
                HOST_NAME = host.getHostName();
            } catch (UnknownHostException e) {
                HOST_NAME = "unknown";
            }
        }
        return HOST_NAME;
    }

    /**
     * 本机所有的物理网络接口
     */
    public static List<String> getAllIPV4() {
        if (IPV4_LIST == null) {
            IPV4_LIST = new LinkedList<>();
            try {
                Enumeration<NetworkInterface> interfs = NetworkInterface.getNetworkInterfaces();
                while (interfs.hasMoreElements()) {
                    NetworkInterface networkInterface = interfs.nextElement();
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress address = inetAddresses.nextElement();
                        if (address instanceof Inet4Address) {
                            String addressStr = address.getHostAddress();
                            if ("127.0.0.1".equals(addressStr)) {
                                continue;
                            }
                            IPV4_LIST.add(addressStr);
                        }
                    }
                }
            } catch (SocketException e) {
            }
        }
        return IPV4_LIST;
    }

    /**
     * 获取本地局域网IP
     */
    public static String getLocalhostAddress () {
        List<String> allIPV4 = getAllIPV4();
        for (String ip : allIPV4) {
            if (ip.startsWith("192")) {
                return ip;
            }
        }
        return null;
    }
}
