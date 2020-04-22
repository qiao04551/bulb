package _20200422;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * Apache RandomStringUtils的使用，生成随机字符串
 * <p>
 * Created by zfh on 2020/04/22
 */
public class RandomStringUtilsTest {

    /**
     * 使用指定字符串生成5位长度的随机字符串
     */
    @Test
    public void testRandom1() {
        String random = RandomStringUtils.random(5, 'a', 'c', 'e', 'j', 'z');
        System.out.println(random);
    }

    /**
     * 生成指定长度的字母和数字的随机字符串组合
     */
    @Test
    public void testRandom2() {
        String s = RandomStringUtils.randomAlphanumeric(5);
        System.out.println(s);
    }

    /**
     * 生成随机数字字符串
     */
    @Test
    public void testRandom3() {
        String s = RandomStringUtils.randomNumeric(5);
        System.out.println(s);
    }

    /**
     * 生成[a-z]字符串，包含大小写
     */
    @Test
    public void testRandom4() {
        String s = RandomStringUtils.randomAlphabetic(5);
        System.out.println(s);
    }

    /**
     * 生成ASCLL 32 到 126 组成的随机字符串
     */
    @Test
    public void testRandom5() {
        String s = RandomStringUtils.randomAscii(5);
        System.out.println(s);
    }
}
