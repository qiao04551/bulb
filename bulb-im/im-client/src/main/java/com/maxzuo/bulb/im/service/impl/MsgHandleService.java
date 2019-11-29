package com.maxzuo.bulb.im.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.bulb.im.client.BIMClient;
import com.maxzuo.bulb.im.common.ChatMessageDTO;
import com.maxzuo.bulb.im.common.LoginMessageDTO;
import com.maxzuo.bulb.im.constant.Const;
import com.maxzuo.bulb.im.service.MsgHandle;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令行消息处理器
 * <p>
 * Created by zfh on 2019/11/29
 */
@Service
public class MsgHandleService implements MsgHandle {

    private static final Logger logger = LoggerFactory.getLogger(MsgHandleService.class);

    @Autowired
    private BIMClient client;

    @Override
    public void login(String line) {
        Map<String, String> param = parseLoginArgs(line);

        LoginMessageDTO messageDTO = new LoginMessageDTO();
        messageDTO.setCommandType(Const.CommandType.LOGIN);
        messageDTO.setUsername(param.get("username"));
        messageDTO.setPassword(param.get("password"));

        byte[] content = JSONObject.toJSONBytes(messageDTO);
        client.sendStringMessage(content);
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

    @Override
    public void sendMsg(String line) {
        Map<String, String> param = parseSendMsgArgs(line);

        ChatMessageDTO messageDTO = new ChatMessageDTO();
        messageDTO.setFrom(param.get("from"));
        messageDTO.setTo(param.get("to"));
        messageDTO.setMsg(param.get("msg"));
        messageDTO.setCommandType(Const.CommandType.MSG);

        byte[] content = JSONObject.toJSONBytes(messageDTO);
        client.sendStringMessage(content);
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
