package com.maxzuo.im.nio.client.scanner;

import com.maxzuo.im.nio.client.BIMClient;
import com.maxzuo.im.nio.common.entity.ChatMessageDTO;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 扫描命令行
 * <p>
 * Created by zfh on 2019/11/29
 */
public class Scan {

    private static final Logger logger = LoggerFactory.getLogger(Scan.class);

    private BIMClient bimClient;

    public Scan(BIMClient bimClient) {
        this.bimClient = bimClient;
    }

    public void start () {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("等待输入...");
            String line = scanner.nextLine();
            if (!checkMsg(line)) {
                continue;
            }
            // 发送消息
            // $ send -from dazuo -to wang -m hello
            if (line.startsWith("send")) {
                sendMsg(line);
            }
        }
    }

    private boolean checkMsg (String msg) {
        if (msg == null || msg.isEmpty()) {
            logger.warn("不能输入空消息！");
            return false;
        }
        return true;
    }

    /**
     * 发送消息给指定用户
     */
    private void sendMsg(String line) {
        Map<String, String> param = parseSendMsgArgs(line);

        ChatMessageDTO messageDTO = new ChatMessageDTO();
        messageDTO.setFrom(param.get("from"));
        messageDTO.setTo(param.get("to"));
        messageDTO.setMsg(param.get("msg"));

        try {
            bimClient.sendMsgToUser(messageDTO);
        } catch (Exception e) {
            logger.warn("【BIM聊天室-客户端】{} 发送消息异常 ", messageDTO.getFrom());
        }
    }

    private Map<String, String> parseSendMsgArgs (String line) {
        String[] args = line.split(" ");
        Options options = new Options();
        options.addOption(new Option("from", "from", true, "发消息的用户"));
        options.addOption(new Option("to", "to", true, "接受消息的用户"));
        options.addOption(new Option("m", "msg", true, "消息体"));
        CommandLineParser parser = new DefaultParser();

        Map<String, String> param = new HashMap<>(10);
        try {
            CommandLine commandLine = parser.parse(options, args);
            Option[] opts = commandLine.getOptions();
            if (opts != null) {
                for (Option opt : opts) {
                    String name = opt.getLongOpt();
                    String value = commandLine.getOptionValue(name);
                    param.put(name, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }
}
