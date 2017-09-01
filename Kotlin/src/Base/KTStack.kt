package Base

/**
 * Stack 链表的实现
 * Stack 是一种基于 LIFO 策略的集合类型
 *
 * @author igaozp
 * @since 2017-08-31
 * @version 1.0
 * @param <Item> 泛型类型
 */
class KTStack<Item> : Iterable<Item> {
    /**
     * 栈的顶部节点
     */
    private var first: Node? = null
    /**
     * 栈的节点元素数量
     */
    private var N = 0

    /**
     * 内部的链表节点类
     */
    private inner class Node {
        internal var item: Item? = null
        internal var next: Node? = null
    }

    /**
     * 检查栈是否为空
     *
     * @return `true` 栈为空
     *         `false` 栈不为空
     */
    fun isEmpty(): Boolean = first == null

    /**
     * 检查栈的节点元素数量
     *
     * @return 节点元素数量
     */
    fun size(): Int = N

    /**
     * 元素压入栈
     *
     * @param item 入栈的元素
     */
    fun push(item: Item) {
        // 使用头插法将元素入栈
        val oldFirst = this.first
        this.first = Node()
        this.first!!.item = item
        this.first!!.next = oldFirst
        // 更新节点数量
        N++
    }

    /**
     * 元素出栈
     *
     * @return 出栈的元素
     */
    fun pop(): Item {
        // 获取头节点的元素，并更新头节点
        val item = first!!.item
        first = first!!.next
        // 更新节点的数量
        N--

        return item!!
    }

    /**
     * 实现 Iterable 接口的 iterator 函数
     *
     * @return 迭代对象
     */
    override fun iterator(): Iterator<Item> {
        return ListIterator()
    }

    /**
     * 自定义 ListIterator 用来实现 Iterable 接口
     */
    private inner class ListIterator : Iterator<Item> {
        private var current = first

        /**
         * 检查是否有下一个元素
         *
         * @return `true` 有下一个元素
         *         `false` 没有下一个元素
         */
        override fun hasNext(): Boolean {
            return current != null
        }

        /**
         * 获取下一个元素
         *
         * @return `Item` 泛型类型的对象
         */
        override fun next(): Item {
            val item = current!!.item
            current = current!!.next
            return item!!
        }
    }
}