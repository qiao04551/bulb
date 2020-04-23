package _20200423;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Java-查看有效的字符编码
 * <p>
 * Created by zfh on 2020/04/23
 */
public class CharsetTest {

    public static void main(String[] args) {
        SortedMap<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = map.entrySet();
        for (Map.Entry<String, Charset> entry : entries) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
