package com.maxzuo.basic;

import org.junit.Test;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Map 家族类
 * <p>
 * Created by zfh on 2019/09/26
 */
public class MapExample {

    /**
     * IdentityHashMap
     * <pre>
     *  1.两者最主要的区别是IdentityHashMap使用的是 == 比较key的值，而HashMap使用的是equals()
     *  2.IdentityHashMap中key能重复，但需要注意一点的是key比较的方法是==，所以若要存放两个相同的key，就需要存放不同的地址。
     *  3.IdentityHashMap有其特殊用途，比如序列化或者深度复制。或者记录对象代理。
     *    举个例子，jvm中的所有对象都是独一无二的，哪怕两个对象是同一个class的对象，而且两个对象的数据完全相同，对于jvm来说，
     *    他们也是完全不同的，如果要用一个map来记录这样jvm中的对象，你就需要用IdentityHashMap，而不能使用其他Map实现。
     * </pre>
     */
    @Test
    public void IdentityHashMap () {
        Map<Integer, Object> data = new IdentityHashMap<>(10);
        data.put(128, "dazuo");
        data.put(128, "dazuo2");
        System.out.println(data);
        // 输出：2
        System.out.println(data.size());
    }
}
