package _20200426;

/**
 * Created by zfh on 2020/04/25
 */
public class DataWithSource implements Comparable<DataWithSource> {

    /**
     * 数值
     */
    private int value;

    /**
     * 记录数值来源的数组
     */
    private int source;

    /**
     * 记录数值在数组中的索引
     */
    private int index;

    DataWithSource(int value, int source, int index) {
        this.value = value;
        this.source = source;
        this.index = index;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }

    int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }

    /**
     * 由于 PriorityQueue 使用小顶堆来实现，这里通过修改两个整数的比较逻辑来让 PriorityQueue 变成大顶堆
     */
    @Override
    public int compareTo(DataWithSource o) {
        return Integer.compare(o.getValue(), this.value);
    }
}
