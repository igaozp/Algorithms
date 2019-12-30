package io.metatom.find;

import io.metatom.base.Queue;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 二分查找（基于有序数组）
 * <p>
 * 二分查找通过对有序数列进行查找，每次查找都使查找的规模缩小一半
 *
 * @param <Key>   用作 key 泛型类型
 * @param <Value> 用作 value 的泛型类型
 * @author igaozp
 * @version 1.1
 * @since 2017-07-04
 */
@SuppressWarnings("unused")
public class BinarySearchST<Key extends Comparable<Key>, Value> {
    /**
     * 默认的初始化大小
     */
    private static final int INIT_CAPACITY = 2;
    /**
     * 存储键的数组
     */
    private Key[] keys;
    /**
     * 存储值的数组
     */
    private Value[] values;
    /**
     * 查找表的长度
     */
    private int size = 0;

    /**
     * 无参构造函数
     */
    public BinarySearchST() {
        this(INIT_CAPACITY);
    }

    /**
     * 构造函数
     *
     * @param capacity 查找表的初始化大小
     */
    public BinarySearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    /**
     * 重新分配表的大小
     *
     * @param capacity 新的表的大小
     */
    private void resize(int capacity) {
        assert capacity >= size;
        var tempOfKeys = (Key[]) new Comparable[capacity];
        var tempOfValues = (Value[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            tempOfKeys[i] = keys[i];
            tempOfValues[i] = values[i];
        }
        values = tempOfValues;
        keys = tempOfKeys;
    }

    /**
     * 检查查找表的长度
     *
     * @return 表的长度
     */
    public int size() {
        return size;
    }

    /**
     * 检查查找表是否为空
     *
     * @return {@code true} 表为空 {@code false} 表不为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 根据给定的键查找节点的值
     *
     * @param key 需要查找的键
     * @return 查找的节点值
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        if (isEmpty()) {
            return null;
        }
        // 查找到的键的坐标
        int i = rank(key);
        if (i < size && keys[i].compareTo(key) == 0) {
            return values[i];
        }
        return null;
    }

    /**
     * 二分查找
     *
     * @param key 需要查找的键
     * @return 查找到的键的下标
     */
    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                // 查找的键小于中间键
                high = mid - 1;
            } else if (cmp > 0) {
                // 查找的键大于中间键
                low = mid + 1;
            } else {
                // 返回找到的相应的值
                return mid;
            }
        }
        return low;
    }

    /**
     * 查找给定的键，找到则更新其值，否则在表中新建节点
     *
     * @param key 需要查找的键
     * @param val 需要更新或插入的值
     */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        int i = rank(key);
        if (i < size && keys[i].compareTo(key) == 0) {
            values[i] = val;
            return;
        }
        // 扩容
        if (size == keys.length) {
            resize(2 * keys.length);
        }
        // 插入新的元素
        for (int j = size; j > i; j--) {
            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }
        keys[i] = key;
        values[i] = val;
        size++;
    }

    /**
     * 删除给定键的节点
     *
     * @param key 需要删除的节点的键
     */
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        // 表已经为空
        if (isEmpty()) {
            return;
        }
        // 删除元素
        int i = rank(key);
        for (int j = i; j < size - 1; j++) {
            keys[j] = keys[j + 1];
            values[j] = values[j + 1];
        }
        size--;
    }

    /**
     * 删除表中的最小元素
     */
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow error");
        }
        delete(min());
    }

    /**
     * 删除表中的最大的元素
     */
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow error");
        }
        delete(max());
    }

    /**
     * 获取最小值的键
     *
     * @return 最小值的键
     */
    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("called min() with empty symbol table");
        }
        return keys[0];
    }

    /**
     * 获取最小值的键
     *
     * @return 最小值的键
     */
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("called max() with empty symbol table");
        }
        return keys[size - 1];
    }

    /**
     * 获取相应的键
     *
     * @param k 键的下标
     * @return 查找到的键
     */
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        return keys[k];
    }

    /**
     * 查找不大于该键的键
     *
     * @param key 查找的键
     * @return 查找到的键
     */
    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        int i = rank(key);
        if (i == size) {
            return null;
        }
        return keys[i];
    }

    /**
     * 查找不小于该键的键
     *
     * @param key 查找的键
     * @return 查找到的键
     */
    public Key floor(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        int i = rank(key);
        if (i < size && key.compareTo(keys[i]) == 0) {
            return keys[i];
        }
        if (i == 0) {
            return null;
        }
        return keys[i - 1];
    }

    /**
     * 获取表的所有的 key
     *
     * @return key 的集合
     */
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    /**
     * 表中所有键的集合
     *
     * @return 包含所有键的队列
     */
    public Iterable<Key> keys(Key low, Key high) {
        if (low == null) {
            throw new IllegalArgumentException("first argument to keys() is null");
        }
        if (high == null) {
            throw new IllegalArgumentException("second argument to keys() is null");
        }
        var queue = new Queue<Key>();
        for (int i = rank(low); i < rank(high); i++) {
            queue.enqueue(keys[i]);
        }
        if (contains(high)) {
            queue.enqueue(keys[rank(high)]);
        }
        return queue;
    }

    /**
     * 检查是否存在给定键的节点
     *
     * @param key 需要查找的键
     * @return {@code true} 节点存在 {@code false} 节点不存在
     */
    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * 正确性检测
     *
     * @return {@code true} 通过 {@code false} 未通过
     */
    private boolean check() {
        return isSorted() && rankCheck();
    }

    /**
     * 检查是否已经排序
     *
     * @return {@code true} 有序 {@code false} 无序
     */
    private boolean isSorted() {
        for (int i = 1; i < size; i++) {
            if (keys[i].compareTo(keys[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 随机检查
     *
     * @return {@code true} 有序 {@code false} 无序
     */
    private boolean rankCheck() {
        for (int i = 0; i < size; i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }
        for (int i = 0; i < size; i++) {
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测 {@code BinarySearchST} 是否可用
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        var st = new BinarySearchST<String, Integer>(5);
        var scanner = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            var key = scanner.next();
            st.put(key, i);
        }

        for (var s : st.keys()) {
            System.out.println(s + " " + st.get(s));
        }
    }
}
