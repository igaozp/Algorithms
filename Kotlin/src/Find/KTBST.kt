package Find

import edu.princeton.cs.algs4.Queue

/**
 * 二叉查找树
 *
 * @author igaozp
 * @since 2017-09-02
 * @version 1.0
 *
 * @param <K> 泛型类型
 * @param <V> 泛型类型
 */
class KTBST<K : Comparable<K>, V> {
    /**
     * 二叉树的根节点
     */
    private var root: Node? = null

    /**
     * 内部定义的节点类
     */
    private inner class Node(internal var k: K, internal var v: V, internal var N: Int) {
        internal var left: Node? = null
        internal var right: Node? = null
    }

    /**
     * 获取二叉树的节点数量
     *
     * @return 节点数量
     */
    fun size(): Int = size(root)

    /**
     * 获取指定节点的子节点数量
     *
     * @param x 指定节点
     * @return 节点数量
     */
    private fun size(x: Node?): Int = x?.N ?: 0

    /**
     * 获取指定的键的值
     *
     * @param key 指定的键
     * @return 指定键的值
     */
    fun get(key: K): V? = get(root, key)

    /**
     * 在指定节点下获取指定键的值
     *
     * @param x 指定的节点
     * @param key 指定的键
     * @return 指定键的值
     */
    private fun get(x: Node?, key: K): V? {
        if (x == null) {
            return null
        }

        val cmp = key.compareTo(x.k)
        return when {
            cmp < 0 -> get(x.left, key)
            cmp > 0 -> get(x.right, key)
            else -> x.v
        }
    }

    /**
     * 向二叉树中添加新的节点
     *
     * @param key 节点的键
     * @param value 节点的值
     */
    fun put(key: K, value: V) {
        root = put(root, key, value)
    }

    /**
     * 在指定节点下添加新的节点
     *
     * @param x 指定的节点
     * @param key 新节点的键
     * @param value 新节点的值
     * @return 添加节点的指定节点
     */
    private fun put(x: Node?, key: K, value: V): Node? {
        if (x == null) {
            return Node(key, value, 1)
        }

        val cmp = key.compareTo(x.k)
        when {
            cmp < 0 -> x.left = put(x.left, key, value)
            cmp > 0 -> x.right = put(x.right, key, value)
            else -> x.v = value
        }

        return x
    }

    /**
     * 获取二叉函数中最小元素的键
     *
     * @return 最小元素的键
     */
    fun min(): K = min(root).k

    /**
     * 在指定节点下获取最小元素的节点
     *
     * @param x 指定的节点
     * @return 最小元素的节点
     */
    private fun min(x: Node?): Node {
        return if (x!!.left == null) {
            x
        } else {
            min(x.left)
        }
    }

    /**
     * 获取二叉函数中最大元素的键
     *
     * @return 最大元素的键
     */
    fun max(): K = max(root).k

    /**
     * 在指定节点大获取最小元素的节点
     *
     * @param x 指定的节点
     * @return 最大元素的节点
     */
    private fun max(x: Node?): Node {
        return if (x!!.right == null) {
            x
        } else {
            max(x.right)
        }
    }

    /**
     * 在二叉树中获取最大的不大于指定键的节点的键
     *
     * @param key 指定的键
     * @return 不大于指定键的键
     */
    fun floor(key: K): K? = floor(root, key)?.k

    /**
     * 在指定的节点下获取最大的不大于指定键的节点
     *
     * @param x 指定节点
     * @param key 指定键
     * @return 最大的节点
     */
    private fun floor(x: Node?, key: K): Node? {
        if (x == null) {
            return null
        }

        val cmp = key.compareTo(x.k)
        if (cmp == 0) {
            return x
        }
        if (cmp < 0) {
            return floor(x.left, key)
        }
        val t = floor(x.right, key)
        return when {
            t != null -> t
            else -> x
        }
    }

    /**
     * 在二叉树中获取最小的不小于指定键的节点的键
     *
     * @param key 指定的键
     * @return 不小于指定键的键
     */
    fun ceiling(key: K): K? = ceiling(root, key)?.k

    /**
     * 在指定的节点下获取最小的不小于指定键的节点
     *
     * @param x 指定节点
     * @param key 指定键
     * @return 最小的节点
     */
    private fun ceiling(x: Node?, key: K): Node? {
        if (x == null) {
            return null
        }

        val cmp = key.compareTo(x.k)
        if (cmp == 0) {
            return x
        }
        if (cmp > 0) {
            return ceiling(x.right, key)
        }
        val t = ceiling(x.left, key)
        return when {
            t != null -> t
            else -> x
        }
    }

    /**
     * 在二叉树中查找指定大小的子树
     *
     * @param k 子树的大小
     * @return 子树的键
     */
    fun select(k: Int): K? = select(root, k)?.k

    /**
     * 在指定节点下根据子树大小查找相应的节点
     *
     * @param x 指定的节点
     * @param k 子树的大小
     * @return 查找到的节点
     */
    private fun select(x: Node?, k: Int): Node? {
        if (x == null) {
            return null
        }
        val t = size(x.left)
        return when {
            t > k -> select(x.left, k)
            t < k -> select(x.right, k - t - 1)
            else -> x
        }
    }

    /**
     * 根据键查找相应节点子树的节点数量
     *
     * @param key 查找的键
     * @return 节点数量
     */
    fun rank(key: K): Int = rank(key, root)

    /**
     * 在指定节点下通过键查找节点的子节点数量
     *
     * @param key 指定的键
     * @param x 指定的节点
     * @return 节点数量
     */
    private fun rank(key: K, x: Node?): Int {
        if (x == null) {
            return 0
        }
        val cmp = key.compareTo(x.k)
        return when {
            cmp < 0 -> rank(key, x.left)
            cmp > 0 -> 1 + size(x.left) + rank(key, x.right)
            else -> size(x.left)
        }
    }

    /**
     * 删除二叉树中最小的节点
     */
    fun deleteMin() {
        root = deleteMin(root)
    }

    /**
     * 在指定的节点下删除最小的节点
     *
     * @param x 指定的节点
     * @return 删除后的指定节点
     */
    private fun deleteMin(x: Node?): Node? {
        if (x!!.left == null) {
            return x.right
        }
        x.left = deleteMin(x.left)
        x.N = size(x.left) + size(x.right) + 1
        return x
    }

    /**
     * 根据键删除相应的节点
     *
     * @param key 需要删除的节点的键
     */
    fun delete(key: K) {
        root = delete(root, key)
    }

    /**
     * 在指定节点下根据键删除节点
     *
     * @param x 指定的节点
     * @param key 指定的键
     * @return 删除后的指定节点
     */
    private fun delete(x: Node?, key: K): Node? {
        var x: Node? = x ?: return null
        val cmp = key.compareTo(x!!.k)
        when {
            cmp < 0 -> x.left = delete(x.left, key)
            cmp > 0 -> x.right = delete(x.right, key)
            else -> {
                if (x.right == null) {
                    return x.left
                }
                if (x.left == null) {
                    return x.right
                }
                val t = x
                x = min(t.right)
                x.right = deleteMin(t.right)
                x.left = t.left
            }
        }
        x.N = size(x.left) + size(x.right) + 1
        return x
    }

    /**
     * 获取二叉树中所有的键
     *
     * @return 所有键的集合
     */
    fun keys(): Iterable<K> = keys(min(),  max())

    /**
     * 根据键的范围获取范围内的键
     *
     * @param lo 开始范围
     * @param hi 结束范围
     * @return 包含键的队列
     */
    private fun keys(lo: K, hi: K): Iterable<K> {
        val queue = Queue<K>()
        keys(root, queue, lo, hi)
        return queue
    }

    /**
     * 在指定节点和范围下获取所有键的集合
     *
     * @param x 指定的节点
     * @param queue 存储键的队列
     * @param lo 开始范围
     * @param hi 结束范围
     */
    private fun keys(x: Node?, queue: Queue<K>, lo: K, hi: K) {
        if (x == null) return

        val cmpLo = lo.compareTo(x.k)
        val cmpHi = hi.compareTo(x.k)
        if (cmpLo < 0) {
            keys(x.left, queue, lo, hi)
        }
        if (cmpLo <= 0 && cmpHi >= 0) {
            queue.enqueue(x.k)
        }
        if (cmpHi > 0) {
            keys(x.right, queue, lo, hi)
        }
    }
}