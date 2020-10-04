package _20201004;

/**
 * 测试数组和Cpu缓存行
 * 1.数组（Array）是一种线性表数据结构。它用一组连续的内存空间，来存储一组具有相同类型的数据。
 * 2.CPU缓存的最小单位是Cpu缓存行，一个缓存行大小通常是64字节(取决于CPU)
 * <p>
 * Created by zfh on 2020/10/04
 */
public class ArrayTest {

    static long[][] arr;

    /**
     * 测试一下二维数组的横向遍历和纵向遍历的具体时间和性能
     * <p>
     * 因横向遍历遍历的是行，然后在循环行的每一列，Cpu缓存会缓存64字节大小的缓存行，所以可以减少cpu和主存之间的交互，
     * 直接和高速缓存交互，提升性能，纵向遍历因每次循环都是不同的行，所以使缓存行没有作用。
     */
    public static void main(String[] args) {
        long sum = 0L;
        arr = new long[1024 * 1024][10];
        // 横向遍历
        long l = System.currentTimeMillis();
        for (int i = 0; i < 1024 * 1024; i++) {
            for (int j = 0; j < 10; j++) {
                sum += arr[i][j];
            }
        }
        System.out.println("Loop Time 横向遍历：" + (System.currentTimeMillis() - l) + "ms");

        long l1 = System.currentTimeMillis();
        // 纵向遍历
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 1024 * 1024; j++) {
                sum += arr[j][i];
            }
        }
        System.out.println("Loop Time 纵向遍历：" + (System.currentTimeMillis() - l1) + "ms");
    }
}
