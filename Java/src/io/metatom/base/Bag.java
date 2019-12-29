package io.metatom.base;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Bag 的链表实现
 * Bag 是一种不支持删除其中元素的集合数据类型，可以用来收集和遍历元素
 *
 * @param <Item> 泛型类型
 * @author igaozp
 * @version 1.3
 * @since 2017-6-30
 */
public class Bag<Item> implements Iterable<Item> {
    /**
     * 链表的首节点
     */
    private Node<Item> first;

    /**
     * Bag 的元素数量
     */
    private int size;

    /**
     * 内部的链表节点类
     */
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * 构造方法
     */
    public Bag() {
        first = null;
        size = 0;
    }

    /**
     * 检查 Bag 是否为空
     *
     * @return {@code true} 为空
     * {@code false} 不为空
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * 检查 Bag 的元素数量
     *
     * @return 元素的数量
     */
    public int size() {
        return size;
    }

    /**
     * 添加元素
     *
     * @param item 添加的元素
     */
    public void add(Item item) {
        // 使用头插法插入新的元素
        Node<Item> oldFirst = this.first;
        this.first = new Node<>();
        this.first.item = item;
        this.first.next = oldFirst;
        size++;
    }

    /**
     * 实现 Iterable 接口的 iterator 函数
     *
     * @return 迭代对象
     */
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator<>(first);
    }

    /**
     * 自定义 ListIterator 用来实现 Iterable 接口
     */
    private static class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        /**
         * 构造方法
         *
         * @param first 构造方法的参数
         */
        ListIterator(Node<Item> first) {
            current = first;
        }

        /**
         * 检查是否有下一个元素
         *
         * @return {@code true} 有下一个元素
         * {@code false} 没有下一个元素
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * 获取下一个元素
         *
         * @return {@code Item} 泛型类型的对象
         */
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * Bag 的单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Bag<String> bag = new Bag<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }

        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }
    }
}
