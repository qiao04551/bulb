package _20200424;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Java 使用标识符跳出多层循环体
 * <p>
 * Created by zfh on 2020/04/23
 */
public class main {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 4));
        // 使用标识符停止本轮多层循环
        outer:
        for (Integer item : list) {
            System.out.println("outside: " + item);
            for (Integer item2 : list) {
                if (item2.equals(3)) {
                    continue outer;
                }
                System.out.println("inside：" + item2);
            }
        }
        System.out.println("//////////////////////////////////////////");
        // 使用标识符，打断多层循环体
        outer:
        for (Integer item : list) {
            System.out.println("outside: " + item);
            for (Integer item2 : list) {
                if (item2.equals(3)) {
                    break outer;
                }
                System.out.println("inside：" + item2);
            }
        }
    }
}
