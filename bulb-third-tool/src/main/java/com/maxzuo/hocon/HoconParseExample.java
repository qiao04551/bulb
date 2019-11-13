package com.maxzuo.hocon;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;

/**
 * HOCON（Human-Optimized Config Object Notation）
 * <p>一个易于使用的配置文件格式；文件通常以 .conf 作为后缀名。
 *
 * <p>组成部分：
 *  - key 是一个键值对字符串中的前一个值
 *  - value 可以是字符串、数字、对象、数组或者布尔值并紧随 key 的后面
 *  - key-value separator 把键和值分离，可以是 : 或者 =
 *  - comment 以 # 或者 // 开头，通常用于提供反馈或说明
 *
 * Created by zfh on 2019/11/13
 */
public class HoconParseExample {

    /**
     * 如需从res目录默认加载，则必须命名为application.conf
     */
    private static final Config config = ConfigFactory.load();

    public static void main(String[] args) {
        // 读取对象
        ConfigObject productDatabase = config.getObject("productDatabase");
        System.out.println(productDatabase);
        ConfigValue usernameValue = productDatabase.get("username");
        System.out.println(usernameValue);

        // 读取对象属性
        String username = config.getString("productDatabase.username");
        System.out.println(username);
    }
}
