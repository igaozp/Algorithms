package Base;

import java.util.Iterator;

/**
 * Stack 链表的实现
 * Stack 是一种基于 LIFO 策略的集合类型
 *
 * @author igaozp
 * @since 2017-6-30
 * @version 1.1
 * @param <Item> 泛型类型
 */
public class Stack<Item> implements Iterable<Item> {
    /**
     * 栈的顶部节点
     */
    private Node first;
    /**
     * 栈的节点元素数量
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
     * 检查栈是否为空
     *
     * @return {@code true} 栈为空
     *         {@code false} 栈不为空
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * 检查栈的节点元素数量
     *
     * @return 节点元素数量
     */
    public int size() {
        return N;
    }

    /**
     * 元素压入栈
     *
     * @param item 入栈的元素
     */
    public void push(Item item) {
        // 使用头插法将元素入栈
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirst;
        // 更新节点数量
        N++;
    }

    /**
     * 元素出栈
     *
     * @return 出栈的元素
     */
    public Item pop() {
        // 获取头节点的元素，并更新头节点
        Item item = first.item;
        first = first.next;
        // 更新节点的数量
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
     * 自定义 ListIterator 用来实现 Iterable 接口
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
         * @return {@code Item} 泛型类型的对象
         */
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
