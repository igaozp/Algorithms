package io.metatom.find

import edu.princeton.cs.algs4.Queue

/**
 * 二分查找（基于有序数组）
 * 二分查找通过对有序数列进行查找，每次查找都使查找的规模缩小一半
 *
 * @author igaozp
 * @since 2017-09-03
 * @version 1.0
 */
class BinarySearchST<K : Comparable<K>, V> constructor(capacity: Int) {
    // 默认的初始化大小
    private val initCapacity = 2
    // 存储键的数组
    private var keys: MutableList<K?> = MutableList(initCapacity) { null }
    // 存储值的数组
    private var vals: MutableList<V?> = MutableList(initCapacity) { null }
    // 查找表的长度
    private var size = 0

    // 构造函数
    init {
        this.keys = MutableList(capacity) { null }
        this.vals = MutableList(capacity) { null }
    }

    // 检查查找表的长度
    fun size(): Int = size

    // 检查查找表是否为空
    fun isEmpty() = size == 0

    // 重新分配表的大小
    private fun resize(capacity: Int) {
        assert(capacity >= size)
        val tempOfKey: MutableList<K?> = MutableList(capacity) { null }
        val tempOfValue: MutableList<V?> = MutableList(capacity) { null }

        for (i in 0 until size) {
            tempOfKey[i] = keys[i]
            tempOfValue[i] = vals[i]
        }
        vals = tempOfValue
        keys = tempOfKey
    }

    // 根据给定的键查找节点的值
    fun get(key: K?): V? {
        if (key == null) throw IllegalArgumentException("argument to get() is null")
        if (isEmpty()) return null
        // 查找到的键的坐标
        val i = rank(key)
        return when {
            i < size && keys[i] == key -> vals[i]
            else -> null
        }
    }

    // 二分查找
    private fun rank(key: K?): Int {
        if (key == null) throw IllegalArgumentException("argument to rank() is null")
        var lo = 0
        var hi = size - 1
        while (lo <= hi) {
            val mid = lo + (hi - lo) / 2
            val cmp = key.compareTo(keys[mid]!!)
            when {
                // 查找的键小于中间键
                cmp < 0 -> hi = mid - 1
                // 查找的键大于中间键
                cmp > 0 -> lo = mid + 1
                // 返回找到的相应的值
                else -> return mid
            }
        }
        return lo
    }

    // 查找给定的键，找到则更新其值，否则在表中新建节点
    fun put(key: K, value: V) {
        val i = rank(key)
        if (i < size && keys[i] == key) {
            vals[i] = value
            return
        }

        if (size == keys.size) resize(2 * keys.size)

        for (j in size downTo i + 1) {
            keys[j] = keys[j - 1]
            vals[j] = vals[j - 1]
        }
        keys[i] = key
        vals[i] = value
        size++
    }

    // 删除给定键的节点
    fun delete(key: K) {
        if (isEmpty()) return
        val i = rank(key)
        for (j in i until (size - 1)) {
            keys[j] = keys[j + 1]
            vals[j] = vals[j + 1]
        }
        size--
    }

    // 删除表中最小的元素
    fun deleteMin() {
        if (isEmpty()) throw NoSuchElementException("Symbol table underflow error")
        delete(min())
    }

    // 删除表中最小的元素
    fun deleteMax() {
        if (isEmpty()) throw NoSuchElementException("Symbol table underflow error")
        delete(max())
    }

    // 获取最小值的键
    fun min(): K = keys[0]!!

    // 获取最小值的键
    fun max(): K = keys[size - 1]!!

    // 获取相应的键
    private fun select(k: Int): K = keys[k]!!

    // 查找不大于该键的键
    fun ceiling(key: K): K? {
        val i = rank(key)
        if (i == size) return null
        return keys[i]
    }

    // 查找不小于该键的键
    fun floor(key: K): K? {
        val i = rank(key)
        if (i < size && key.compareTo(keys[i]!!) == 0) return keys[i]
        if (i == 0) return null
        return keys[i - 1]
    }

    // 表中指定范围键的集合
    fun keys(lo: K, hi: K): Iterable<K> {
        val q = Queue<K>()
        for (i in rank(lo) until rank(hi)) {
            q.enqueue(keys[i])
        }
        if (contains(hi)) q.enqueue(keys[rank(hi)])
        return q
    }

    // 表中所有键的集合
    fun keys(): Iterable<K> = keys(min(), max())

    // 检查是否存在给定键的节点
    fun contains(key: K) = get(key) != null

    // 正确性检测
    private fun check() = isSorted() && rankCheck()

    // 检查是否已经排序
    private fun isSorted(): Boolean {
        for (i in 0 until size) {
            if (keys[i]!! < keys[i - 1]!!) return false
        }
        return true
    }

    // 随机检查
    private fun rankCheck(): Boolean {
        for (i in 0 until size) {
            if (i != rank(select(i))) return false
        }
        for (i in 0 until size) {
            if (keys[i]!!.compareTo(select(rank(keys[i]))) != 0) return false
        }
        return true
    }
}

// 单元测试
fun main() {
    val st = BinarySearchST<String, Int>(2)

    for (i in 0 until 5) {
        st.put(i.toString(), i)
    }

    println("size of binary search table = " + st.size())

    for (k in st.keys()) {
        println(k)
    }
}