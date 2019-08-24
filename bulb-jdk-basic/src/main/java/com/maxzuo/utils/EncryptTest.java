package com.maxzuo.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

/**
 * Apache commons-codec用于摘要运算、编码解码的包，常见的编码解码工具Base64、MD5、Hex、SHA1、DES等。
 * Created by zfh on 2019/01/05
 */
public class EncryptTest {

    /**
     * MD5加密，生成16进制MD5字符串（32位字符串）
     * {@link org.apache.commons.codec.digest.DigestUtils}
     */
    @Test
    public void md5HexTest() {
        String encryptStr = DigestUtils.md5Hex("hello world");
        System.out.println("encryptStr:" + encryptStr);
    }

    /**
     * base64的加解密
     * {@link org.apache.commons.codec.binary.Base64}
     */
    @Test
    public void testBase64() {
        Base64 base64 = new Base64();
        String base64EncodeStr = base64.encodeToString("hello world".getBytes());
        System.out.println("encode string: " + base64EncodeStr);

        byte[] decode = base64.decode(base64EncodeStr);
        System.out.println("decode string：" + new String(decode));
    }

    /**
     * sha1和sha255加密
     * {@link org.apache.commons.codec.digest.DigestUtils}
     */
    @Test
    public void testSha() {
        String sha1Hex = DigestUtils.sha1Hex("hello world");
        System.out.println("sha1Hex: " + sha1Hex);

        String sha256Hex = DigestUtils.sha256Hex("hello world");
        System.out.println("sha256Hex: " + sha256Hex);
    }

    /**
     * 实现“www-form-urlencoding”编码方案，也被错误地称为URL编码。
     * Implements the 'www-form-urlencoded' encoding scheme, also misleadingly known as URL encoding.
     * {@link org.apache.commons.codec.net.URLCodec}
     */
    @Test
    public void testUrlCodec() throws UnsupportedEncodingException, DecoderException {
        URLCodec urlCodec = new URLCodec();
        String encodeStr = urlCodec.encode("a/b", "UTF-8");
        System.out.println("encodeStr: " + encodeStr);

        String decodeStr = urlCodec.decode(encodeStr, "UTF-8");
        System.out.println("decodeStr: " + decodeStr);
    }
}
