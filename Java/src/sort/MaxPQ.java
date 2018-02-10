package sort;

import edu.princeton.cs.algs4.Heap;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 基于堆的优先队列
 * <p>
 * 优先队列由一个基于堆的完全二叉树表示，
 * 二叉树的每个节点元素都大于它的子节点元素，根节点为二叉树的最大节点
 *
 * @param <Key> 泛型类型
 * @author igaozp
 * @version 1.0
 * @since 2017-7-3
 */
public class MaxPQ<Key> implements Iterable<Key> {
    /**
     * 存储基于堆的完全二叉树
     */
    private Key[] pq;
    /**
     * 队列的大小
     */
    private int N;
    /**
     * 比较器
     */
    private Comparator<Key> comparator;

    /**
     * 构造方法
     */
    public MaxPQ() {
        this(1);
    }

    /**
     * 基于堆的优先队列的构造方法
     *
     * @param initCapacity 优先队列初始化的长度
     */
    public MaxPQ(int initCapacity) {
        this.pq = (Key[]) new Comparable[initCapacity + 1];
        this.N = 0;
    }

    /**
     * 构造方法
     *
     * @param initCapacity 优先队列初始化的长度
     * @param comparator   比较器
     */
    public MaxPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        this.pq = (Key[]) new Object[initCapacity + 1];
        this.N = 0;
    }

    /**
     * 构造方法
     *
     * @param comparator 比较器
     */
    public MaxPQ(Comparator<Key> comparator) {
        this(1, comparator);
    }

    /**
     * 构造方法
     *
     * @param keys 初始化的元素数组
     */
    public MaxPQ(Key[] keys) {
        this.N = keys.length;
        this.pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < N; i++) {
            pq[i + 1] = keys[i];
        }
        for (int k = N / 2; k >= 1; k--) {
            sink(k);
        }
        assert isMaxHeap();
    }

    /**
     * 检查队列是否为空
     *
     * @return {@code true} 队列为空
     * {@code false} 队列不为空
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * 检查队列的大小
     *
     * @return 队列的大小
     */
    public int size() {
        return N;
    }

    /**
     * 获取最大的元素
     *
     * @return 最大的元素
     */
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return pq[1];
    }

    /**
     * 更改队列大小
     *
     * @param capacity 新的尺寸
     */
    private void resize(int capacity) {
        assert capacity > N;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= N; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    /**
     * 向队列插入元素
     *
     * @param v 插入的元素
     */
    public void insert(Key v) {
        if (N == pq.length - 1) {
            resize(2 * pq.length);
        }
        pq[++N] = v;
        swim(N);
        assert isMaxHeap();
    }

    /**
     * 删除队列的最大元素
     *
     * @return 队列的最大元素
     */
    public Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        Key max = pq[1];
        exch(1, N--);
        pq[N + 1] = null;
        sink(1);
        if ((N > 0) && (N == (pq.length - 1) / 4)) {
            resize(pq.length / 2);
        }
        return max;
    }

    /**
     * 比较两个参数的大小
     *
     * @param i 比较的第一个参数
     * @param j 比较的第二个参数
     * @return {@code true} 第一个参数比第二个小
     * {@code false} 第一个参数比第二个大
     */
    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        } else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    /**
     * 交换元素
     *
     * @param i 交换的第一个元素的下标
     * @param j 交换的第二个元素的下标
     */
    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    /**
     * 由下至上的堆的有序化的实现
     * <p>
     * 如果堆的有序状态被打破，需要将新插入的节点与父节点交换来修复，
     * 交换后如果仍是无序状态，则重复该步骤
     *
     * @param k 需要有序化的元素下标
     */
    private void swim(int k) {
        // 如果该节点大于父节点，则与父节点交换位置
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    /**
     * 由上至下的堆的有序化的实现
     * <p>
     * 如果堆的有序状态被打破，需要将该节点与两个子节点中较大的子节点来交换，
     * 直到它的子节点都比它小或者到达堆的底部
     *
     * @param k 需要有序化的元素下标
     */
    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            // 寻找子节点中较大的一个
            if (j < N && less(j, j + 1)) {
                j++;
            }
            // 如果该节点大于子节点，则完成任务
            if (!less(k, j)) {
                break;
            }
            // 与子节点交换
            exch(k, j);
            k = j;
        }
    }

    /**
     * 检查是否是最大堆
     *
     * @return {@code true} 是最大堆
     * {@code false} 不是最大堆
     */
    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    /**
     * 检查指定节点下是否是最大堆
     *
     * @param k 指定的节点
     * @return {@code true} 是最大堆
     * {@code false} 不是最大堆
     */
    private boolean isMaxHeap(int k) {
        if (k > N) return true;
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= N && less(k, left)) return false;
        if (right <= N && less(k, right)) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }

    @NotNull
    @Override
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {
        private MaxPQ<Key> copy;

        public HeapIterator() {
            if (comparator == null) {
                copy = new MaxPQ<>(size());
            } else {
                copy = new MaxPQ<>(size(), comparator);
            }
            for (int i = 1; i <= N; i++) {
                copy.insert(pq[i]);
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.delMax();
        }
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        MaxPQ<String> pq = new MaxPQ<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                pq.insert(item);
            } else if (!pq.isEmpty()) {
                StdOut.print(pq.delMax() + " ");
            }
        }
        StdOut.println("(" + pq.size() + " left on pq");
    }
}
