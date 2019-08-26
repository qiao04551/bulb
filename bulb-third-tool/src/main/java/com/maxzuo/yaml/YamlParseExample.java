package com.maxzuo.yaml;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * YAML配置文件解析器：SnakeYAML
 * <pre>
 *   使用SnakeYAML来读取YAML（YML为其简写）配置文件；读取后，装载成Map。
 *
 *   参考文章：
 *     <a href="https://my.oschina.net/u/3471006/blog/1616373">YAML配置文件解析器：SnakeYAML</a>
 * </pre>
 * Created by zfh on 2019/03/30
 */
public class YamlParseExample {

    private static final String CONFIG_FILE = "/application.yml";

    public static void main(String[] args) {
        InputStream inputStream = readConfigFile();
        Yaml yaml = new Yaml();
        Map map = yaml.loadAs(inputStream, Map.class);
        System.out.println(map);

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取yml配置文件
     */
    private static InputStream readConfigFile() {
        return YamlParseExample.class.getResourceAsStream(CONFIG_FILE);
    }
}
