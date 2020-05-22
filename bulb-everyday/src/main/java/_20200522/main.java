package _20200522;

/**
 * 面试题：Java中的参数传递时传值呢?还是传引用？
 *
 * <p>答：无论是基本类型，还是引用变量，Java中的参数传递都是值复制的传递过程。对于引用变量，复制指向对象的首地址，双方都可以通过自己
 * 的引用变量修改指向对象的相关属性。
 *
 * <p>首先要理解"="赋值操作的意义：
 * 1.对于基本数据类型来说"="赋值操作是直接改变内存地址（存储单元）上的值。
 * 2.对于引用类型来说"="赋值操作是改变引用变量所指向的内存地址（上下文存储单页）。
 *
 * Created by zfh on 2020/05/22
 */
public class main {

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();

        changeData(builder);
        builder.append("3");
        System.out.println(builder.toString());
    }

    private static void changeData(StringBuilder builder) {
        builder = new StringBuilder();
        builder.append("2");
        System.out.println(builder.toString());
    }
}
