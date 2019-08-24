package com.maxzuo.singleton;

/**
 * 单例模式
 * <p>
 * Created by zfh on 2019/08/24
 */
public class SingletonExample {
    public static void main(String[] args) {
        UserOne.getInstance();
    }
}

/**
 * 懒汉模式
 * <pre>
 *   这种方式在类加载时就完成了初始化，所以类加载较慢，但获取对象的速度快。 这种方式基于类加载机制避免了多线程的同步问题，
 *   但是也不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化instance显然没有达到懒加载的效果。
 * </pre>
 */
class UserOne {

    private static UserOne instance = new UserOne();

    public static UserOne getInstance() {
        return instance;
    }
}

/**
 * 懒汉模式（线程不安全）
 * <pre>
 *   懒汉模式申明了一个静态对象，在用户第一次调用时初始化，虽然节约了资源，但第一次加载时需要实例化，
 *   反映稍慢一些，而且在多线程不能正常工作。
 * </pre>
 */
class UserTwo {

    private static UserTwo instance;

    public static UserTwo getInstance() {
        if (instance == null) {
            instance = new UserTwo();
        }
        return instance;
    }
}

/**
 * 懒汉模式（线程安全）
 * <pre>
 *   这种写法能够在多线程中很好的工作，但是每次调用getInstance方法时都需要进行同步，造成不必要的同步开销，
 *   而且大部分时候我们是用不到同步的，所以不建议用这种模式。
 * </pre>
 */
class UserThree {

    private static UserThree instance;

    public static synchronized UserThree getInstance() {
        if (instance == null) {
            instance = new UserThree();
        }
        return instance;
    }
}

/**
 * 双重检查模式 （DCL）
 * <pre>
 *   建议用静态内部类单例模式来替代DCL。
 *
 *   双重检测机制不是线程安全，存在指令重排：
 *     理想的指令执行顺序
 *       1.memory = allocate() 分配对象的内存空间
 *       2.instance = memory 设置intance指向刚分配的内存
 *       3.ctorInstance() 初始化对象
 *
 *     实际的指令执行执行顺序
 *       1.memory = allocate() 分配对象的内存空间
 *       3.ctorInstance() 初始化对象
 *       2.instance = memory 设置intance指向刚分配的内存
 *
 *     解决方法：使用volatile禁止指令重排
 * </pre>
 */
class UserFour {

    private volatile static UserFour instance;

    public static UserFour getInstance() {
        if (instance == null) {
            synchronized (UserFour.class) {
                if (instance == null) {
                    instance = new UserFour();
                }
            }
        }
        return instance;
    }
}

/**
 * 静态内部类单例模式
 * <pre>
 *   第一次加载Singleton类时并不会初始化sInstance，只有第一次调用getInstance方法时虚拟机加载SingletonHolder 并初始化sInstance，
 *   这样不仅能确保线程安全也能保证Singleton类的唯一性，所以推荐使用静态内部类单例模式。
 * </pre>
 */
class UserFive {

    public static UserFive getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final UserFive sInstance = new UserFive();
    }
}

/**
 * 枚举单例
 * <pre>
 *   默认枚举实例的创建是线程安全的，并且在任何情况下都是单例，上述讲的几种单例模式实现中，有一种情况下他们会重新创建对象，
 *   那就是反序列化，将一个单例实例对象写到磁盘再读回来，从而获得了一个实例。反序列化操作提供了readResolve方法，这个方法
 *   可以让开发人员控制对象的反序列化。在上述的几个方法示例中如果要杜绝单例对象被反序列化是重新生成对象
 * </pre>
 */
class UserSix {

    private enum Singleton {
        INSTANCE;

        private UserSix singleton;

        /** JVM保证这个方法绝对只会执行一次 */
        Singleton() {
            singleton = new UserSix();
        }

        public UserSix getInstance() {
            return singleton;
        }
    }
}