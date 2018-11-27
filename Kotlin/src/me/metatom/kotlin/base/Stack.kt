package me.metatom.kotlin.base

import edu.princeton.cs.algs4.Stack

/**
 * Stack 链表的实现
 * Stack 是一种基于 LIFO 策略的集合类型
 *
 * @author igaozp
 * @since 2017-08-31
 * @version 1.0
 */
class Stack<Item> : Iterable<Item> {
    // 栈的顶部节点
    private var first: Node? = null
    // 栈的节点元素数量
    private var size = 0

    // 内部的链表节点类
    private inner class Node {
        var item: Item? = null
        var next: Node? = null
    }


    // 检查栈是否为空
    fun isEmpty(): Boolean = first == null

    // 检查栈的节点元素数量
    fun size(): Int = size

    // 元素压入栈
    fun push(item: Item) {
        // 使用头插法将元素入栈
        val oldFirst = this.first
        this.first = Node()
        this.first!!.item = item
        this.first!!.next = oldFirst
        // 更新节点数量
        size++
    }

    // 元素出栈
    fun pop(): Item {
        if (isEmpty()) throw NoSuchElementException("Stack underflow")

        // 获取头节点的元素，并更新头节点
        val item = first!!.item
        first = first!!.next
        // 更新节点的数量
        size--

        return item!!
    }

    // 获取栈顶的元素
    fun peek(): Item? {
        if (isEmpty()) throw NoSuchElementException("Stack underflow")
        return first!!.item
    }

    override fun toString(): String {
        val s = StringBuilder()
        for (item in this) {
            s.append(item)
            s.append(' ')
        }
        return s.toString()
    }

    // 实现 Iterable 接口的 iterator 函数
    override fun iterator(): Iterator<Item> = ListIterator()

    // 自定义 ListIterator 用来实现 Iterable 接口
    private inner class ListIterator : Iterator<Item> {
        private var current = first

        // 检查是否有下一个元素
        override fun hasNext() = current != null

        // 获取下一个元素
        override fun next(): Item {
            if (!hasNext()) throw NoSuchElementException()
            val item = current!!.item
            current = current!!.next
            return item!!
        }
    }
}

// 单元测试
fun main(args: Array<String>) {
    val stack = Stack<String>()
    stack.push("1")
    stack.push("2")
    stack.push("3")
    stack.push("4")
    stack.push("5")
    println("size of stack = " + stack.size())

    while (!stack.isEmpty) {
        println(stack.pop())
    }
    println("size of stack = " + stack.size())
}