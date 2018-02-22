package base

/**
 * Bag 的链表实现
 * Bag 是一种不支持删除其中元素的集合数据类型，可以用来收集和遍历元素
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-08-30
 */
class KBag<Item> : Iterable<Item> {
    // 链表的首节点
    private var first: Node? = null
    // Bag 中元素的数量
    private var size: Int = 0

    // 内部的链表节点类
    private inner class Node {
        var item: Item? = null
        var next: Node? = null
    }

    // 检查 Bag 是否为空
    fun isEmpty() = first == null

    // 检查 Bag 的元素数量
    fun size() = size

    // 向 Bag 中添加元素
    fun add(item: Item) {
        // 使用头插法插入新的元素
        val oldFirst = this.first
        this.first = Node()
        this.first!!.item = item
        this.first!!.next = oldFirst
        size++
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

fun main(args: Array<String>) {
    var bag = Bag<String>()
    bag.add("Hello")
    bag.add("World")
    println("size of bag = " + bag.size())
    bag.forEach { s -> println(s) }
}