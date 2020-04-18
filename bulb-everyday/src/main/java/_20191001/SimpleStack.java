package _20191001;

/**
 * Java用数组实现一个栈（栈是先进先出的结构）
 * <p>
 * Created by zfh on 2019/10/01
 */
public class SimpleStack<T> {

    /**
     * 存放数据
     */
    private T[] data;

    /**
     * 数组大小
     */
    private int size;

    /**
     * 数组的元素个数
     */
    private int count;

    public SimpleStack (int size) {
        this.data = (T[]) new Object[size];
        this.size = size;
        this.count = 0;
    }

    public void push (T e) {
        if (count == size) {
            throw new RuntimeException("The stack is full");
        }
        data[count++] = e;
    }

    public T pop () {
        if (count == 0) {
            return null;
        }
        return data[--count];
    }

    public int stackSize () {
        return count;
    }

    public static void main(String[] args) {
        SimpleStack<String> stack = new SimpleStack<>(10);
        stack.push("hello");
        System.out.println(stack.pop());
    }
}
