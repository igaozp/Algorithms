package Sort;

/**
 * 基于堆的优先队列
 *
 * 优先队列由一个基于堆的完全二叉树表示，
 * 二叉树的每个节点元素都大于它的子节点元素，根节点为二叉树的最大节点
 *
 * @author igaozp
 * @since 2017-7-3
 * @version 1.0
 * @param <Key> 泛型类型
 */
public class MaxPQ<Key extends Comparable<Key>> {
    /**
     * 存储基于堆的完全二叉树
     */
    private Key[] pq;
    /**
     * 队列的大小
     */
    private int N = 0;

    /**
     * 基于堆的优先队列的构造方法
     *
     * @param maxN 优先队列的长度
     */
    public MaxPQ(int maxN) {
        this.pq = (Key[]) new Comparable[maxN + 1];
    }

    /**
     * 检查队列是否为空
     *
     * @return {@code true} 队列为空
     *         {@code false} 队列不为空
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
     * 向队列插入元素
     *
     * @param v 插入的元素
     */
    public void insert(Key v) {
        pq[++N] = v;
        swim(N);
    }

    /**
     * 删除队列的最大元素
     *
     * @return  队列的最大元素
     */
    public Key delMax() {
        Key max = pq[1];
        exch(1, N--);
        pq[N + 1] = null;
        sink(1);
        return max;
    }

    /**
     * 比较两个参数的大小
     *
     * @param i 比较的第一个参数
     * @param j 比较的第二个参数
     * @return {@code true} 第一个参数比第二个小
     *         {@code false} 第一个参数比第二个大
     */
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
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
     *
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
     *
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
}
