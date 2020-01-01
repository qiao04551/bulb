package com.maxzuo.email;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * 发送邮件
 * <p>
 * Created by zfh on 2020/01/01
 */
public class EmailExample {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.ssl.enable", true);

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);

        try {
            // 发件人
            message.setFrom(new InternetAddress("863329112@qq.com"));
            // 收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("863329112@qq.com"));
            message.setSubject("测试邮件");
            Multipart multipart = new MimeMultipart();
            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText("hello world");
            multipart.addBodyPart(contentPart);

            // 添加附件
            BodyPart bodyPart = new MimeBodyPart();
            String fileName = "/Users/dazuo/workplace/bulb/LICENSE.txt";
            DataSource dataSource = new FileDataSource(fileName);
            bodyPart.setDataHandler(new DataHandler(dataSource));
            bodyPart.setFileName(MimeUtility.encodeWord("声明"));
            multipart.addBodyPart(bodyPart);

            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.qq.com", "863329112@qq.com", "xxx");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
