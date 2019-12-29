package io.metatom.base;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 使用链表实现的 Queue 队列
 * Queue 队列是一种基于 FIFO 策略的集合类型
 *
 * @param <Item> 泛型类型
 * @author igaozp
 * @version 1.2
 * @since 2017-6-30
 */
@SuppressWarnings("unused")
public class Queue<Item> implements Iterable<Item> {
    /**
     * 队列的首节点
     */
    private Node<Item> first;
    /**
     * 队列的最后一个节点
     */
    private Node<Item> last;
    /**
     * 队列元素的数量
     */
    private int size;

    /**
     * 内部的链表节点类
     */
    private static class Node<Item> {
        Item item;
        Node<Item> next;
    }

    /**
     * 构造方法
     */
    public Queue() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * 检查队列是否为空
     *
     * @return {@code true} 队列为空
     * {@code false} 队列不为空
     */
    public boolean isEmpty() {
        return this.first == null;
    }

    /**
     * 检查队列的元素数量
     *
     * @return 元素数量
     */
    public int size() {
        return size;
    }

    /**
     * 获取队列的头部节点元素
     *
     * @return 队列的头部节点元素
     */
    public Item peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }

        return first.item;
    }

    /**
     * 向队列插入元素
     *
     * @param item 插入的元素
     */
    public void enqueue(Item item) {
        // 新增节点
        Node<Item> oldLast = this.last;
        this.last = new Node<>();
        this.last.item = item;
        this.last.next = null;
        // 根据队列是否为空，选择插入队列的策略
        if (isEmpty()) {
            this.first = this.last;
        } else {
            oldLast.next = this.last;
        }
        // 更新队列元素数量
        size++;
    }

    /**
     * 首节点元素出队
     *
     * @return 出队的元素
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }

        // 获取队列首节点元素，并使下一个元素成为队列的首节点
        Item item = this.first.item;
        this.first = this.first.next;
        // 队列若为空，队列尾节点置空
        if (isEmpty()) {
            this.last = null;
        }
        // 更新队列元素数量
        size--;
        return item;
    }

    /**
     * 重写 {@code toString()} 方法
     *
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
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
     * 自定义 ListIterator 类用来实现 Iterable 接口
     */
    private static class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        /**
         * 构造方法
         *
         * @param first 构造方法参数
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

        /**
         * 元素移除方法
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * 获取下一个元素
         *
         * @return Item 泛型类型的对象
         */
        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            } else if (!queue.isEmpty()) {
                StdOut.println(queue.dequeue() + " ");
            }
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }
}
