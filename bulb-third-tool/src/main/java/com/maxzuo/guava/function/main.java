package com.maxzuo.guava.function;

import com.google.common.base.Function;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Guava函数式编程：
 * <p>
 * Created by zfh on 2020/04/27
 */
public class main {

    /**
     * 优点：可以使用依赖注入来传递一个函数接口到一个协作的类中，使得代码高内聚
     */
    private Function<Date, String> DateFormatFunc = date -> {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        return dateFormat.format(date);
    };

    @Test
    public void testFunction() {
        String dateStr = DateFormatFunc.apply(new Date());
        System.out.println(dateStr);
    }
}
