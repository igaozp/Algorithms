package base

/**
 * 使用链表实现的 Queue 队列
 * Queue 队列是一种基于 FIFO 策略的集合类型
 *
 * @author igaozp
 * @version 1.1
 * @since 2017-08-30
 */
class KQueue<Item> : Iterable<Item> {
    // 队列的首节点
    private var first: Node? = null
    // 队列的最后一个节点
    private var last: Node? = null
    // 队列元素的数量
    private var size = 0

    // 内部的链表节点类
    private inner class Node {
        var item: Item? = null
        var next: Node? = null
    }

    // 检查队列是否为空
    fun isEmpty() = this.first == null

    // 检查队列的元素数量
    fun size(): Int = size

    // 获取队列头部的元素
    fun peek(): Item? {
        if (isEmpty()) throw NoSuchElementException("Queue underflow")
        return first!!.item
    }

    // 向队列插入元素
    fun enqueue(item: Item) {
        // 新增节点
        val oldLast = this.last
        this.last = Node()
        this.last!!.item = item
        this.last!!.next = null
        // 根据队列是否为空，选择插入队列的策略
        if (isEmpty()) {
            this.first = this.last
        } else {
            oldLast!!.next = this.last
        }
        // 更新队列元素数量
        size++
    }

    // 首节点元素出队
    fun dequeue(): Item {
        if (isEmpty()) throw NoSuchElementException("Queue underflow")

        // 获取队列首节点元素，并使下一个元素成为队列的首节点
        val item = this.first!!.item
        this.first = this.first!!.next
        // 队列若为空，队列尾节点置空
        if (isEmpty()) {
            this.last = null
        }
        // 更新队列元素数量
        size--
        return item!!
    }

    override fun toString(): String {
        val s = StringBuilder()
        for (item: Item in this) {
            s.append(item)
            s.append(' ')
        }
        return s.toString()
    }

    // 实现 Iterable 接口的 iterator 函数
    override fun iterator(): Iterator<Item> = ListIterator()

    // 自定义 ListIterator 类用来实现 Iterable 接口
    private inner class ListIterator : Iterator<Item> {
        private var current: Node? = first

        // 检查是否有下一个元素
        override fun hasNext(): Boolean = current != null

        // 获取下一个元素
        override fun next(): Item {
            if (!hasNext()) throw NoSuchElementException()
            val item: Item? = current!!.item
            current = current!!.next
            return item!!
        }
    }
}

// 单元测试
fun main(args: Array<String>) {
    val queue = Queue<String>()
    queue.enqueue("Hello")
    queue.enqueue("World")

    println("size of queue = " + queue.size())
    queue.forEach { i -> println(i) }

    while (!queue.isEmpty) {
        queue.dequeue()
    }
    println("size of queue = " + queue.size())
}