package com.maxzuo.im.bio.client.scanner;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.im.bio.client.BIMClient;
import com.maxzuo.im.bio.common.entity.ChatMessageDTO;
import com.maxzuo.im.bio.common.entity.LoginMessageDTO;
import com.maxzuo.im.bio.common.entity.Message;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.maxzuo.im.bio.common.constant.Const.CommandType;

/**
 * 扫描命令行
 * <p>
 * Created by zfh on 2019/11/29
 */
public class Scan {

    private static final Logger logger = LoggerFactory.getLogger(Scan.class);

    private BIMClient bimClient;

    public Scan (BIMClient bimClient) {
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
            // 登录
            // $ login -u dazuo -p 123
            if (line.startsWith("login")) {
                login(line);
                continue;
            }
            // 发送消息
            // $ send -from dazuo -to wang -m hello
            if (line.startsWith("send")) {
                sendMsg(line);
                continue;
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
     * 用户登录
     */
    private void login(String line) {
        Map<String, String> param = parseLoginArgs(line);

        LoginMessageDTO messageDTO = new LoginMessageDTO();
        messageDTO.setUsername(param.get("username"));
        messageDTO.setPassword(param.get("password"));

        Message message = new Message();
        message.setCommandType(CommandType.LOGIN);
        message.setPayLoad(JSONObject.toJSONString(messageDTO));
        try {
            bimClient.sendMsgToUser(message);
        } catch (Exception e) {
            logger.warn("【BIM聊天室-客户端】{} 登录异常 ", messageDTO.getUsername());
        }
    }

    /**
     * 处理登录参数
     */
    private Map<String, String> parseLoginArgs (String line) {
        String[] args = line.split(" ");
        Options options = new Options();
        options.addOption(new Option("u", "username", true, "登录的用户名"));
        options.addOption(new Option("p", "password", true, "登录的密码"));
        CommandLineParser parser = new DefaultParser();

        Map<String, String> param = new HashMap<>(4);
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
            logger.error("无法解析登录参数！", e);
        }
        return param;
    }

    /**
     * 发送消息给指定用户
     */
    public void sendMsg(String line) {
        Map<String, String> param = parseSendMsgArgs(line);

        ChatMessageDTO messageDTO = new ChatMessageDTO();
        messageDTO.setFrom(param.get("from"));
        messageDTO.setTo(param.get("to"));
        messageDTO.setMsg(param.get("msg"));

        Message message = new Message();
        message.setCommandType(CommandType.MSG);
        message.setPayLoad(JSONObject.toJSONString(messageDTO));

        try {
            bimClient.sendMsgToUser(message);
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
