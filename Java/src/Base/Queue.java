package Base;

import java.util.Iterator;

/**
 * 使用链表实现的 Queue 队列
 * Queue 队列是一种基于 FIFO 策略的集合类型
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-6-30
 * @param <Item> 泛型类型
 */
public class Queue<Item> implements Iterable<Item> {
    /**
     * 队列的首节点
     */
    private Node first;
    /**
     * 队列的最后一个节点
     */
    private Node last;
    /**
     * 队列元素的数量
     */
    private int N;

    /**
     * 内部的链表节点类
     */
    private class Node {
        Item item;
        Node next;
    }

    /**
     * 检查队列是否为空
     *
     * @return {@code true} 队列为空
     *         {@code false} 队列不为空
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
        return N;
    }

    /**
     * 向队列插入元素
     *
     * @param item 插入的元素
     */
    public void enqueue(Item item) {
        // 新增节点
        Node oldLast = this.last;
        this.last = new Node();
        this.last.item = item;
        this.last.next = null;
        // 根据队列是否为空，选择插入队列的策略
        if (isEmpty()) {
            this.first = this.last;
        } else {
            oldLast.next = this.last;
        }
        // 更新队列元素数量
        N++;
    }

    /**
     * 首节点元素出队
     *
     * @return 出队的元素
     */
    public Item dequeue() {
        // 获取队列首节点元素，并使下一个元素成为队列的首节点
        Item item = this.first.item;
        this.first = this.first.next;
        // 队列若为空，队列尾节点置空
        if (isEmpty()) {
            this.last = null;
        }
        // 更新队列元素数量
        N--;
        return item;
    }

    /**
     * 实现 Iterable 接口的 iterator 函数
     *
     * @return 迭代对象
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * 自定义 ListIterator 类用来实现 Iterable 接口
     */
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        /**
         * 检查是否有下一个元素
         *
         * @return {@code true} 有下一个元素
         *         {@code false} 没有下一个元素
         */
        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
        }

        /**
         * 获取下一个元素
         *
         * @return Item 泛型类型的对象
         */
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
