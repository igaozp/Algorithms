package Find;

import Base.Queue;

/**
 * 二分查找（基于有序数组）
 *
 * 二分查找通过对有序数列进行查找，每次查找都使查找的规模缩小一半
 *
 * @author igaozp
 * @since 2017-07-04
 * @version 1.0
 *
 * @param <Key>  用作 key 泛型类型
 * @param <Value> 用作 value 的泛型类型
 */
public class BinarySearchST<Key extends Comparable<Key>, Value> {
    /**
     * 存储键的数组
     */
    private Key[] keys;
    /**
     * 存储值的数组
     */
    private Value[] vals;
    /**
     * 查找表的长度
     */
    private int N;

    /**
     * 构造函数
     *
     * @param capacity 查找表的初始化大小
     */
    public BinarySearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    /**
     * 检查查找表的长度
     *
     * @return 表的长度
     */
    public int size() {
        return N;
    }

    /**
     * 检查查找表是否为空
     *
     * @return {@code true} 表为空
     *         {@code false} 表不为空
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * 根据给定的键查找节点的值
     *
     * @param key 需要查找的键
     * @return 查找的节点值
     */
    public Value get(Key key) {
        if (isEmpty()) {
            return null;
        }
        // 查找到的键的坐标
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) {
            return vals[i];
        } else {
            return null;
        }
    }

    /**
     * 二分查找
     *
     * @param key 需要查找的键
     * @return 查找到的键的下标
     */
    public int rank(Key key) {
        int lo = 0;
        int hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                // 查找的键小于中间键
                hi = mid - 1;
            } else if (cmp > 0) {
                // 查找的键大于中间键
                lo = mid + 1;
            } else {
                // 超找到相应的值
                return mid;
            }
        }
        return lo;
    }

    /**
     * 查找给定的键，找到则更新其值，否则在表中新建节点
     *
     * @param key 需要查找的键
     * @param val 需要更新或插入的值
     */
    public void put(Key key, Value val) {
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }
        for (int j = N; j > 1; j--) {
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    /**
     * 删除给定键的节点
     *
     * @param key 需要删除的节点的键
     */
    public void delete(Key key) {
        int i = rank(key);
        for (int j = i; j < N - 1; j++) {
            keys[j] = keys[j + 1];
            vals[j] = vals[j + 1];
        }
        N--;
    }

    /**
     * 获取最小值的键
     *
     * @return 最小值的键
     */
    public Key min() {
        return keys[0];
    }

    /**
     * 获取最小值的键
     *
     * @return 最小值的键
     */
    public Key max() {
        return keys[N - 1];
    }

    /**
     * 获取相应的键
     *
     * @param k 键的下标
     * @return 查找到的键
     */
    public Key select(int k) {
        return keys[k];
    }

    /**
     * 查找不大于该键的键
     *
     * @param key 查找的键
     * @return 查找到的键
     */
    public Key ceiling(Key key) {
        int i = rank(key);
        return keys[i];
    }

    /**
     * 查找不小于该键的键
     *
     * @param key 查找的键
     * @return 查找到的键
     */
    public Key floor(Key key) {
        int i = rank(key);
        return keys[i + 1];
    }

    /**
     * 表中所有键的集合
     *
     * @return 包含所有键的队列
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> q = new Queue<Key>();
        for (int i = rank(lo); i < rank(hi); i++) {
            q.enqueue(keys[i]);
        }
        if (contains(hi)) {
            q.enqueue(keys[rank(hi)]);
        }
        return q;
    }

    /**
     * 检查是否存在给定键的节点
     *
     * @param key 需要查找的键
     * @return {@code true} 节点存在
     *         {@code false} 节点不存在
     */
    public boolean contains(Key key) {
        for (int i = 0; i < N; i++) {
            if (keys[i].equals(key)) {
                return true;
            }
        }
        return false;
    }
}
