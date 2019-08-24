package com.maxzuo.proxy.bytebuddy.model;

/**
 * Created by zfh on 2019/01/29
 */
public class Target {

    public Target() {
        System.out.println("init target constructor");
    }

    public String method(String name) {
        return "init intecept " + name;
    }

    /** @Argument(0) 接收第一个参数 */
    //public static String methodOne (@Argument(0) String var1) {
    //    return "methodOne: " + var1;
    //}

    /** @AllArguments 接收数组 */
    //public static String methodTwo (@AllArguments String[] array) {
    //    System.out.println("methodTwo: " + Arrays.toString(array));
    //    return null;
    //}

    /** @This 接收源实例对象的引用 */
    //public static String methodThree (@This Object object) {
    //    System.out.println("methodThree: " + object);
    //    return null;
    //}

    /// TODO：待定
    //public static String methodFour (@Super Object object) {
    //    System.out.println("methodFour: " + object);
    //    return null;
    //}

    //public static String intecept (String name) {
    //    return "intecept " + name;
    //}
    //
    //public static String intecept (int i) {
    //    return Integer.toString(i);
    //}
    //
    //public static String intecept (Object o) {
    //    return "Object " + o.toString();
    //}
}
