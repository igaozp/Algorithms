package io.metatom.find

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
class SquentialSearchST<K, V> {
    // 链表首节点
    private var first: Node? = null
    // 表的元素数量
    private var size = 0

    // 内部定义的链表节点
    private inner class Node(var key: K, var value: V?, var next: Node?)

    // 查找表的元素数量
    fun size() = size

    // 通过键 key 获取节点值
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

    // 查找给定的键，找到则更新其值，否则在表中新建节点
    fun put(key: K, value: V?) {
        if (value == null) {
            delete(key)
            return
        }
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
        first = Node(key, value, first)
        size++
    }

    // 删除给定键的节点
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
                size--
            }
            x = x.next
        }
    }

    // 检查是否存在给定键的节点
    fun contains(key: K): Boolean = get(key) != null

    // 检查链表是否为空
    fun isEmpty(): Boolean = size == 0

    // 表中所有键的集合
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

// 单元测试
fun main() {
    val st = SquentialSearchST<String, Int>()
    for (i in 0 until 10) {
        st.put(i.toString(), i)
    }

    println("size of search table = " + st.size())

    for (s in st.keys()) {
        println(s)
    }
}