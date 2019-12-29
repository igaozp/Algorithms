package io.metatom.find;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import io.metatom.base.Queue;

/**
 * 基于拉链法的散列表
 * <p>
 * 将数组中的每一元素指向一条链表，链表中的每一节点都存储了散列值为该元素的索引的键值对
 *
 * @param <Key>   泛型类型
 * @param <Value> 泛型类型
 * @author igaozp
 * @version 1.1
 * @since 2017-07-10
 */
@SuppressWarnings({"unused", "DuplicatedCode"})
public class SeparateChainingHashST<Key, Value> {
    /**
     * 默认的初始化大小
     */
    private static final int INIT_CAPACITY = 4;
    /**
     * 键值对总数
     */
    private int N;
    /**
     * 散列表的大小
     */
    private int M;
    /**
     * 存放链表对象的数组
     */
    private SequentialSearchST<Key, Value>[] st;

    /**
     * 无参构造方法
     */
    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * 带有参数的构造方法
     *
     * @param M 初始化的散列表大小
     */
    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    /**
     * 调整散列表的大小
     *
     * @param chains 新的散列表的大小
     */
    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<>(chains);
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.M = temp.M;
        this.N = temp.N;
        this.st = temp.st;
    }

    /**
     * 哈希函数，获取键值对中键的哈希值
     *
     * @param key 键值对的键
     * @return 哈希值
     */
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * 获取哈希表的大小
     *
     * @return 哈希表的大小
     */
    public int size() {
        return N;
    }

    /**
     * 检查哈希表是否为空
     *
     * @return {@code true} 哈希表为空
     * {@code false} 哈希表不为空
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 获取指定键对应的元素
     *
     * @param key 指定的键
     * @return 指定键的元素
     */
    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }

        return get(key) != null;
    }

    /**
     * 根据键获取相应的值
     *
     * @param key 需要获取值的键
     * @return 键对应的值
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }

        return st[hash(key)].get(key);
    }

    /**
     * 向散列表中添加新的键值对
     *
     * @param key 键值对的键
     * @param val 键值对的值
     */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) {
            delete(key);
            return;
        }

        if (N >= 10 * M) {
            resize(2 * M);
        }

        int i = hash(key);
        if (!st[i].contains(key)) {
            N++;
        }

        st[i].put(key, val);
    }

    /**
     * 删除元素
     *
     * @param key 删除元素的键
     */
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        int i = hash(key);
        if (st[i].contains(key)) {
            N--;
        }
        st[i].delete(key);

        if (M > INIT_CAPACITY && N <= 2 * M) {
            resize(M / 2);
        }
    }

    /**
     * 获取散列表中所有键的集合
     *
     * @return 包含键的队列
     */
    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<>();
        for (int i = 0; i < M; i++) {
            Queue<Key> temp = (Queue<Key>) st[i].keys();
            for (Key key : temp) {
                q.enqueue(key);
            }
        }
        return q;
    }

    /**
     * 哈希表的单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}
