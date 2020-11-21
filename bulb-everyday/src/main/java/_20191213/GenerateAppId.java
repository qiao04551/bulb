package _20191213;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * 分配AppId 和 AppSecret
 * <p>
 * Created by zfh on 2020/11/21
 */
public class GenerateAppId {

    public static void main(String[] args) {
        String appId = generateAppId();
        System.out.println(appId);

        String appSecret = generateAppSecret();
        System.out.println(appSecret);
    }

    private static String generateAppId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    private static String generateAppSecret() {
        UUID uuid = UUID.randomUUID();
        return DigestUtils.sha256Hex(uuid.toString());
    }
}
