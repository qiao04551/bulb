package com.maxzuo.openfire;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * openfire 客户端
 * <p>
 * Created by zfh on 2019/08/19
 */
public class OpenfireClient {

    private XMPPTCPConnection conn;

    public static void main(String[] args) {
        try {
            OpenfireClient client = new OpenfireClient();
            client.connect();

            client.login("wang", "123456");
            client.sendMessage("123");

            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化连接
     */
    private void connect () throws IOException, InterruptedException, XMPPException, SmackException {
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setXmppDomain("47.98.199.80")
                .setHost("47.98.199.80")
                .setPort(5222)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .build();

        conn = new XMPPTCPConnection(config);
        conn.connect();
        System.out.println("连接状态：" + conn.isConnected());
    }

    /**
     * 注册
     */
    private void register (String username, String password) throws XMPPException.XMPPErrorException, SmackException.NotConnectedException, InterruptedException, SmackException.NoResponseException, XmppStringprepException {
        AccountManager manager = AccountManager.getInstance(conn);
        Localpart localpart = Localpart.from(username);
        manager.sensitiveOperationOverInsecureConnection(true);
        manager.createAccount(localpart, password);
    }

    /**
     * 登录
     */
    private void login (String username,String password){
        try {
            if(conn!=null){
                conn.login(username, password);
                System.out.println("user "+username+" login successfully.");
            }
        } catch (Exception e) {
            System.out.println("登录异常！");
            e.printStackTrace();
        }
    }

    /**
     * 发送消息（需先登录）
     */
    private void sendMessage (String msg){
        // 构建聊天室
        ChatManager cm = ChatManager.getInstanceFor(conn);
        cm.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
                System.out.println("Incoming message: " + message.getBody());
            }
        });

        cm.addOutgoingListener(new OutgoingChatMessageListener() {
            @Override
            public void newOutgoingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
                System.out.println("Outgoing Message：" + message.getBody());
            }
        });

        try {
            EntityBareJid jid = JidCreate.entityBareFrom("dazuo@47.98.199.80");
            Chat chat = cm.chatWith(jid);
            Message message = new Message();
            message.setBody(msg);
            chat.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
