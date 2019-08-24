package com.maxzuo.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdater 原子更新字段类
 * <p>
 * Created by zfh on 2019/08/24
 */
public class AtomicIntegerFieldUpdateExample {

    private static AtomicIntegerFieldUpdater<User> atom = AtomicIntegerFieldUpdater.newUpdater(User.class, "id");

    public static void main(String[] args) {
        User user = new User(1, "dazuo");
        System.out.println(atom.addAndGet(user, 2));

        // 通过CAS函数操作
        System.out.println(atom.compareAndSet(user, 3, 4));
    }
}

@Data
@AllArgsConstructor
class User {

    /**
     * 约束和限制
     *   1.字段必须是 volatile 类型的，在线程之间共享变量时保证立即可见。
     *   2.字段的描述类型（修饰符public/protected/default/private）是与调用者与操作对象的关系一致。也就是说调用者能够直接操作对象字段，
     *     那么就可以反射进行原子操作。但是对于父类的字段，子类是不能直接操作的，尽管子类可以访问父类的字段。
     *   3.只能是实例变量，不能是类变量，也就是说不能加static关键字。
     *   4.只能是可修改变量，不能是final变量，因为final的语句就是不可修改。
     *   5.对于 AtomicIntegerFieldUpdate 和 AtomicLongFieldUpdate 只能修改 int/long 类型的字段，不能修改包装类型。
     *     如果要修改包装类型就需要使用 AtomicReferenceFieldUpdate。
     */
    public volatile int id;

    private String name;
}
