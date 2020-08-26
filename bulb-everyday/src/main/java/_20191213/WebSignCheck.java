package _20191213;

import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Web签名校验
 * <p>
 *   第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
 *   使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
 *   第二步，在stringA最后拼接上appsecret得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到
 *   的字符串所有字符转换为大写，得到sign值signValue。
 * </p>
 * <p>
 *   假设传送的参数如下：
 *   appid： abcdefg123567
 *   total_amount： 600
 *   body： ipadpro
 *   detail： ipadpro
 *   nonce_str： ibuaiVcKdpRxkhJA
 *
 *   第一步：对参数按照key=value的格式，并按照参数名ASCII字典序排序如下：
 *   stringA=’appid=abcdefg123567&body=ipadpro&detail=ipadpro&nonce_str=ibuaiVcKdpRxkhJA&total_amount=600’;
 *   第二步：拼接API密钥：
 *   stringSignTemp=stringA+’&appsecret=192006250b4c09247ec02edce69f6a2d’
 *   sign=MD5(stringSignTemp).toUpperCase()='84E6FDA29F49DD369FB4FD88D6D3464A’
 * </p>
 * Created by zfh on 2019/11/23
 */
public class WebSignCheck {

    private static final String APPID = "ivv49q404zfp8075ivbcwye4ardqafha";

    private static final String APP_SECRET = "ut338c829x2yzfnklvy8lezyu3ndsss68dyzo9opt3icbin7lv7p2j4b0i2cvjz8";

    public static void main(String[] args) {
        WebSignCheck webSignCheck = new WebSignCheck();
        PlaceOrderForm form = webSignCheck.mockWebForm();

        try {
            Map<String, String> stringStringMap = confirmToMap(form);
            System.out.println(stringStringMap);

            String sign = sign(stringStringMap);
            System.out.println(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟web请求对象
     */
    private PlaceOrderForm mockWebForm () {
        PlaceOrderForm form = new PlaceOrderForm();
        form.setAppid(APPID);
        form.setBody("test body");
        form.setDetail("test detail");
        form.setNonceStr(createNonceStr(10));
        form.setTotalAmount(88);
        return form;
    }

    /**
     * TreeMap会根据Key排序
     */
    private static Map<String, String> confirmToMap(PlaceOrderForm form) throws Exception {
        Map<String, String> map = new TreeMap<>();
        Field[] fields = PlaceOrderForm.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(form);
            if (value != null && !field.getName().equals("sign")) {
                if (value instanceof String) {
                    map.put(field.getName(), (String) value);
                } else if (value instanceof Integer) {
                    map.put(field.getName(), String.valueOf(value));
                }
            }
        }
        return map;
    }

    /**
     * 生成签名
     */
    private static String sign(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        sb.append("appsecret=");
        sb.append(APP_SECRET);
        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }

    /**
     * 生成随机字符串
     * @param lenth 长度
     */
    private static String createNonceStr (int lenth) {
        String[] chars = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d",
                "e", "f", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
                "v", "w", "x", "y", "z"};

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lenth; i++) {
            int nextIndex = new Random().nextInt(chars.length);
            builder.append(chars[nextIndex]);
        }
        return builder.toString();
    }
}
