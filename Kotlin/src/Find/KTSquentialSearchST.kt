package Find

import edu.princeton.cs.algs4.Queue

/**
 * 顺序查找（基于无序链表）
 *
 * 使用无序链表构造查找表，表的每一个节点包含一个键和值，通过使用键来查找相应的值
 *
 * @author igaozp
 * @since 2017-09-04
 * @version 1.0
 */
class KTSquentialSearchST<K, V> {
    /**
     * 链表首节点
     */
    private var first: Node? = null

    /**
     * 内部定义的链表节点
     *
     * @param key 初始化的键
     * @param value 初始化的值
     * @param next 初始化的下一个节点
     */
    private inner class Node(internal var key: K, internal var value: V?, internal var next: Node?)

    /**
     * 通过键 key 获取节点值
     *
     * @param key 需要查找的键
     * @return 查找到的值
     */
    fun get(key: K): V? {
        var x = first
        while (x != null) {
            if (key == x.key) {
                return x.value
            }
            x = x.next
        }
        return null
    }

    /**
     * 查找给定的键，找到则更新其值，否则在表中新建节点
     *
     * @param key 需要查找的键
     * @param value 需要更新或插入的值
     */
    fun put(key: K, value: V) {
        var x = first
        while (x != null) {
            // 查找到更新节点的值
            if (key == x.key) {
                x.value = value
                return
            }
            x = x.next
        }
        // 没有查找到，则新建节点
        first = Node(key, value, first!!)
    }

    /**
     * 删除给定键的节点
     *
     * @param key 需要删除的节点的键
     */
    fun delete(key: K) {
        // 删除的节点的前一个节点
        var prev = first
        var x = first
        while (x != null) {
            if (x != first) {
                prev = prev!!.next
            }
            if (key == x.key) {
                x.value = null
                prev!!.next = x.next
            }
            x = x.next
        }
    }

    /**
     * 检查是否存在给定键的节点
     *
     * @param key 需要查找的键
     * @return `true` 节点存在
     *         `false` 节点不存在
     */
    fun contains(key: K): Boolean {
        var x = first
        while (x != null) {
            if (key == x.key) return true
            x = x.next
        }
        return false
    }

    /**
     * 检查链表是否为空
     *
     * @return `true` 链表为空
     *         `false` 链表不为空
     */
    fun isEmpty(): Boolean = first == null

    /**
     * 检查链表的长度
     *
     * @return 链表长度
     */
    fun size(): Int {
        var size = 0
        var x = first
        while (x != null) {
            size++
            x = x.next
        }
        return size
    }

    /**
     * 表中所有键的集合
     *
     * @return 包含所有键的队列
     */
    fun keys(): Iterable<K> {
        // 所有的键保存到队列中
        val q = Queue<K>()
        var x = first
        while (x != null) {
            q.enqueue(x.key)
            x = x.next
        }
        return q
    }
}