package com.maxzuo.bulb.im.scanner;

import com.maxzuo.bulb.im.service.MsgHandle;
import com.maxzuo.bulb.im.util.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * 扫描命令行
 * <p>
 * Created by zfh on 2019/11/29
 */
public class Scan implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Scan.class);

    private MsgHandle msgHandle;

    public Scan () {
        msgHandle = SpringBeanFactory.getBean(MsgHandle.class);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (!checkMsg(line)) {
                continue;
            }
            // 登录
            // $ login -u dazuo -p 123
            if (line.startsWith("login")) {
                msgHandle.login(line);
                continue;
            }
            // 发送消息
            // $ send -from dazuo -to wang -m hello
            if (line.startsWith("send")) {
                msgHandle.sendMsg(line);
                continue;
            }
            System.out.println("等待输入...");
        }
    }

    private boolean checkMsg (String msg) {
        if (msg == null || msg.isEmpty()) {
            logger.warn("不能输入空消息！");
            return false;
        }
        return true;
    }
}
