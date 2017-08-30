package Base

/**
 * Bag 的链表实现
 * Bag 是一种不支持删除其中元素的集合数据类型，可以用来收集和遍历元素
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-08-30
 * @param <Item> 泛型类型
 */
class Bag<Item> : Iterable<Item> {
    /**
     * 链表的首节点
     */
    private var first: Node? = null

    /**
     * 内部的链表节点类
     */
    private inner class Node {
        internal var item: Item? = null
        internal var next: Node? = null
    }

    /**
     * 添加元素
     *
     * @param item 添加的元素
     */
    fun add(item: Item) {
        // 使用头插法插入新的元素
        val oldFirst = this.first
        this.first = Node()
        this.first!!.item = item
        this.first!!.next = oldFirst
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
         * 移除元素， Bag 中不需要实现
         */
        fun remove() {}

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