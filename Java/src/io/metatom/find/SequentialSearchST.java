package io.metatom.find;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import io.metatom.base.Queue;

/**
 * 顺序查找表（基于无序链表）
 * <p>
 * 使用无序链表构造查找表，表的每一个节点包含一个键和值，通过使用键来查找相应的值
 *
 * @param <Key>   用于 key 的泛型类型
 * @param <Value> 用于 value 的泛型类型
 * @author igaozp
 * @version 1.0
 * @since 2017-07-04
 */
public class SequentialSearchST<Key, Value> {
    /**
     * 表的元素数量
     */
    private int size;
    /**
     * 链表首节点
     */
    private Node first;

    /**
     * 内部定义的链表节点
     */
    private class Node {
        // 节点的键
        private Key key;
        // 节点的值
        private Value val;
        // 下一个节点
        private Node next;

        /**
         * 节点的构造函数
         *
         * @param key  节点的键
         * @param val  节点的值
         * @param next 下一个节点
         */
        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 构造方法
     */
    SequentialSearchST() {
    }

    /**
     * 查找表的元素数量
     *
     * @return 元素数量
     */
    public int size() {
        return this.size;
    }

    /**
     * 通过键 key 获取节点值
     *
     * @param key 需要查找的键
     * @return 查找到的值
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.val;
            }
        }
        return null;
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
        if (val == null) {
            delete(key);
            return;
        }

        for (Node x = first; x != null; x = x.next) {
            // 查找到更新节点的值
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        // 没有查找到，则新建节点
        first = new Node(key, val, first);
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
        first = delete(first, key);
    }

    /**
     * 从制定的节点开始删除指定的键
     *
     * @param x   指定的节点
     * @param key 指定的键
     * @return 删除后的节点
     */
    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        if (key.equals(x.key)) {
            size--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

    /**
     * 检查是否存在给定键的节点
     *
     * @param key 需要查找的键
     * @return {@code true} 节点存在
     * {@code false} 节点不存在
     */
    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * 检查链表是否为空
     *
     * @return {@code true} 链表为空
     * {@code false} 链表不为空
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 表中所有键的集合
     *
     * @return 包含所有键的队列
     */
    public Iterable<Key> keys() {
        // 所有的键保存到队列中
        Queue<Key> q = new Queue<>();
        for (Node x = first; x != null; x = x.next) {
            q.enqueue(x.key);
        }
        return q;
    }

    /**
     * 线性查询表的单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}
