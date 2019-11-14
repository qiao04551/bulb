package com.maxzuo.guava.enums;

import com.google.common.base.Converter;
import com.google.common.base.Enums;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Guava enums 工具的使用
 * <p>
 * Created by zfh on 2019/09/16
 */
public class EnumsExample {

    /**
     * 获取变量值的 Field
     */
    @Test
    public void testGetField () {
        SourceType type = SourceType.APP;
        Field field = Enums.getField(type);
        System.out.println(field);
    }

    @Test
    public void testGetIfPresent () {
        // APP不存在, 则返回默认的MINI_APP
        SourceType type = Enums.getIfPresent(SourceType.class, "APP").or(SourceType.MINI_APP);
        System.out.println(type);
    }

    @Test
    public void testStringConverter () {
        Converter<String, SourceType> converter = Enums.stringConverter(SourceType.class);
        // 将enum名字字符串转换成指定类型的enum
        SourceType type = converter.convert(SourceType.APP.name());
        System.out.println(type);

        SourceType type1 = Enum.valueOf(SourceType.class, "APP");
        System.out.println(type1);
    }
}
