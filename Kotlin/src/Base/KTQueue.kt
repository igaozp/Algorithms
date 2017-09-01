package Base

/**
 * 使用链表实现的 Queue 队列
 * Queue 队列是一种基于 FIFO 策略的集合类型
 *
 * @author igaozp
 * @version 1.1
 * @since 2017-08-30
 * @param <Item> 泛型类型
 */
class KTQueue<Item>: Iterable<Item> {
    /**
     * 队列的首节点
     */
    private var first: Node? = null
    /**
     * 队列的最后一个节点
     */
    private var last: Node? = null
    /**
     * 队列元素的数量
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
     * 检查队列是否为空
     *
     * @return `true` 队列为空
     *         `false` 队列不为空
     */
    fun isEmpty(): Boolean = this.first == null

    /**
     * 检查队列的元素数量
     *
     * @return 元素数量
     */
    fun size(): Int = N

    /**
     * 向队列插入元素
     *
     * @param item 插入的元素
     */
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
        N++
    }

    /**
     * 首节点元素出队
     *
     * @return 出队的元素
     */
    fun dequeue(): Item {
        // 获取队列首节点元素，并使下一个元素成为队列的首节点
        val item = this.first!!.item
        this.first = this.first!!.next
        // 队列若为空，队列尾节点置空
        if (isEmpty()) {
            this.last = null
        }
        // 更新队列元素数量
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
     * 自定义 ListIterator 类用来实现 Iterable 接口
     */
    private inner class ListIterator : Iterator<Item> {
        private var current: Node? = null

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
         * @return Item 泛型类型的对象
         */
        override fun next(): Item {
            val item: Item? = current!!.item
            current = current!!.next
            return item!!
        }
    }
}