package _20200827;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 腾讯会议 API 计算签名 https://cloud.tencent.com/document/product/1095/42413
 * 拼接好待签名的字符串后，用 SecretKey 密钥生成待签名字符串的 Hmac-SHA256 签名，将签名转换为16进制字符串形式，然后进行 Base64 编码。
 * <p>
 * Created by zfh on 2020/08/27
 */
public class Hmac {

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    static String bytesToHex(byte[] bytes) {

        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }
        return new String(buf);
    }

    public static String sign(String tobeSig, String appSecret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm());
        mac.init(secretKeySpec);

        byte[] hash = mac.doFinal(tobeSig.getBytes(StandardCharsets.UTF_8));
        String hexHash = bytesToHex(hash);
        return new String(Base64.getEncoder().encode(hexHash.getBytes(StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        String appSecret = "12345";
        String tobeSig = "123";
        String sign = sign(tobeSig, appSecret);
        System.out.println(sign);
    }
}
