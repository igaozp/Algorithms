package io.metatom.find

import edu.princeton.cs.algs4.Queue
import edu.princeton.cs.algs4.SequentialSearchST

/**
 * 基于拉链法的散列表
 * 将数组中的每一元素指向一条链表，链表中的每一节点都存储了散列值为该元素的索引的键值对
 *
 * @author igaozp
 * @since 2017-09-04
 * @version 1.0
 */
class SeparateChainingHashST<K, V>(private var M: Int) {
    private val initCapacity = 4
    // 键值对总数
    private var N = 0
    // 存放链表对象的数组
    private var st = MutableList<SequentialSearchST<K, V>?>(initCapacity) { null }

    // 构造函数
    init {
        st = MutableList(M) { null } as MutableList<SequentialSearchST<K, V>?>
        for (i in 0 until M) {
            st[i] = SequentialSearchST()
        }
    }

    // 调整散列表的大小
    private fun resize(chains: Int) {
        val temp = SeparateChainingHashST<K, V>(chains)
        for (i in 0 until M) {
            for (key in st[i]!!.keys()) {
                temp.put(key, st[i]!!.get(key))
            }
        }
        this.M = temp.M
        this.N = temp.N
        this.st = temp.st
    }

    // 哈希函数，获取键值对中键的哈希值
    private fun hash(key: K): Int = (key!!.hashCode() and 0x7fffffff) % M

    // 获取哈希表的大小
    fun size() = N

    // 检查哈希表是否为空
    fun isEmpty() = size() == 0

    // 检查指定键对应的元素，是否存在
    fun contains(key: K) = get(key) != null

    // 根据键获取相应的值
    fun get(key: K): V = st[hash(key)]!!.get(key)

    // 向散列表中添加新的键值对
    fun put(key: K, value: V?) {
        if (value == null) {
            delete(key)
            return
        }
        if (N >= 10 * M) resize(2 * M)
        val hash = hash(key)
        if (!st[hash]!!.contains(key)) N++
        st[hash]!!.put(key, value)
    }

    // 删除指定的元素
    fun delete(key: K) {
        val hash = hash(key)
        if (st[hash]!!.contains(key)) N--
        st[hash]!!.delete(key)
        if (M > initCapacity && N <= 2 * M) resize(M / 2)
    }

    // 获取散列表中所有键的集合
    fun keys(): Iterable<K> {
        val q = Queue<K>()
        (0 until M)
                .map { st[it]!!.keys() as Queue<K> }
                .flatten()
                .forEach { q.enqueue(it) }
        return q
    }
}

// 单元测试
fun main() {
    val st = SeparateChainingHashST<String, Int>(10)
    for (i in 0 until 10) st.put(i.toString(), i)

    println("size of separate chaining hash search table = " + st.size())

    for (node in st.keys()) {
        print("$node\t")
    }
}