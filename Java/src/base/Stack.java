package base;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Stack 链表的实现
 * Stack 是一种基于 LIFO 策略的集合类型
 *
 * @param <Item> 泛型类型
 * @author igaozp
 * @version 1.2
 * @since 2017-6-30
 */
public class Stack<Item> implements Iterable<Item> {
    /**
     * 栈的顶部节点
     */
    private Node<Item> first;
    /**
     * 栈的节点元素数量
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
    public Stack() {
        first = null;
        size = 0;
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
        return size;
    }

    /**
     * 元素压入栈
     *
     * @param item 入栈的元素
     */
    public void push(Item item) {
        // 使用头插法将元素入栈
        Node<Item> oldFirst = this.first;
        this.first = new Node<>();
        this.first.item = item;
        this.first.next = oldFirst;
        // 更新节点数量
        size++;
    }

    /**
     * 元素出栈
     *
     * @return 出栈的元素
     */
    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }

        // 获取头节点的元素，并更新头节点
        Item item = first.item;
        first = first.next;
        // 更新节点的数量
        size--;

        return item;
    }

    /**
     * 获取栈的首节点的元素
     *
     * @return 首节点元素
     */
    public Item peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        return first.item;
    }

    /**
     * 重写 {@code toString()} 方法
     *
     * @return toString
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
    @NotNull
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator<>(first);
    }

    /**
     * 自定义 ListIterator 用来实现 Iterable 接口
     */
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        /**
         * 构造方法
         *
         * @param first 构造方法参数
         */
        public ListIterator(Node<Item> first) {
            current = first;
        }

        /**
         * 检查是否有下一个元素
         *
         * @return {@code true} 有下一个元素
         *         {@code false} 没有下一个元素
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * remove 方法
         */
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
     * Stack 的单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                stack.push(item);
            } else if (!stack.isEmpty()) {
                StdOut.print(stack.pop() + " ");
            }
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}
