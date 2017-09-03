package Find

import Base.Queue

/**
 * 二分查找（基于有序数组）
 *
 * 二分查找通过对有序数列进行查找，每次查找都使查找的规模缩小一半
 *
 * @author igaozp
 * @since 2017-09-03
 * @version 1.0
 *
 */
class KTBinarySearchST<K: Comparable<K>, V>(capacity: Int) {
    /**
     * 存储键的数组
     */
    private var keys: Array<K>? = null
    /**
     * 存储值的数组
     */
    private var vals: Array<V>? = null
    /**
     * 查找表的长度
     */
    private var N = 0

    /**
     * 构造函数
     */
    init {
        this.keys = Array(capacity)
        this.vals = Array(capacity)
    }

    /**
     * 初始化数组的辅助函数
     */
    private fun <T> Array(capacity: Int): Array<T> = Array(capacity)

    /**
     * 检查查找表的长度
     *
     * @return 表的长度
     */
    fun size(): Int = N

    /**
     * 检查查找表是否为空
     *
     * @return `true` 表为空
     *         `false` 表不为空
     */
    fun isEmpty(): Boolean = N == 0

    /**
     * 根据给定的键查找节点的值
     *
     * @param key 需要查找的键
     * @return 查找的节点值
     */
    fun get(key: K): V? {
        if (isEmpty()) return null
        // 查找到的键的坐标
        val i = rank(key)
        return when {
            i < N && keys!![i].compareTo(key) == 0 -> vals!![i]
            else -> null
        }
    }

    /**
     * 二分查找
     *
     * @param key 需要查找的键
     * @return 查找到的键的下标
     */
    fun rank(key: K): Int {
        var lo = 0
        var hi = N - 1
        while (lo <= hi) {
            val mid = lo + (hi - lo) / 2
            val cmp = key.compareTo(keys!![mid])
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

    /**
     * 查找给定的键，找到则更新其值，否则在表中新建节点
     *
     * @param key 需要查找的键
     * @param value 需要更新或插入的值
     */
    fun put(key: K, value: V) {
        val i = rank(key)
        if (i < N && keys!![i].compareTo(key) == 0) {
            vals!![i] = value
            return
        }
        for (j in N downTo 2) {
            keys!![j] = keys!![j - 1]
            vals!![j] = vals!![j - 1]
        }
        keys!![i] = key
        vals!![i] = value
        N++
    }

    /**
     * 删除给定键的节点
     *
     * @param key 需要删除的节点的键
     */
    fun delete(key: K) {
        val i = rank(key)
        for (j in i until (N - 1)) {
            keys!![j] = keys!![j + 1]
            vals!![j] = vals!![j + 1]
        }
        N--
    }

    /**
     * 获取最小值的键
     *
     * @return 最小值的键
     */
    fun min(): K = keys!![0]

    /**
     * 获取最小值的键
     *
     * @return 最小值的键
     */
    fun max(): K = keys!![N - 1]

    /**
     * 获取相应的键
     *
     * @param k 键的下标
     * @return 查找到的键
     */
    fun select(k: Int): K = keys!![k]

    /**
     * 查找不大于该键的键
     *
     * @param key 查找的键
     * @return 查找到的键
     */
    fun ceiling(key: K): K = keys!![rank(key)]

    /**
     * 查找不小于该键的键
     *
     * @param key 查找的键
     * @return 查找到的键
     */
    fun floor(key: K): K = keys!![rank(key)]

    /**
     * 表中所有键的集合
     *
     * @return 包含所有键的队列
     */
    fun keys(lo: K, hi: K): Iterable<K> {
        val q = Queue<K>()
        for (i in rank(lo) until rank(hi)) {
            q.enqueue(keys!![i])
        }
        if (contains(hi)) {
            q.enqueue(keys!![rank(hi)])
        }
        return q
    }

    /**
     * 检查是否存在给定键的节点
     *
     * @param key 需要查找的键
     * @return `true` 节点存在
     *         `false` 节点不存在
     */
    fun contains(key: K): Boolean = (0 until N).any { keys!![it] == key }
}