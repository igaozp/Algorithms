package io.metatom.find

import edu.princeton.cs.algs4.Queue

/**
 * 红黑二叉查找树
 *
 * 红黑树是一种二叉树，且红链接均为左链接，没有任何一个节点同时和两条红色链接相连，
 * 该树是完美黑色平衡的，即任意链接到根节点的路径上的黑链接数量相同。
 *
 * @author igaozp
 * @since 2017-09-05
 * @version 1.0
 */
class RedBlackBST<K : Comparable<K>, V> {
    // 红黑树节点颜色，使用 true 代表红色，false 代表黑色
    private val red = true
    private val black = false

    // 红黑树的根节点
    private var root: Node? = null

    // 用于红黑树的内部节点
    inner class Node(var key: K, var value: V, var N: Int, var color: Boolean) {
        var left: Node? = null
        var right: Node? = null
    }

    // 检查红黑树的节点数量
    fun size(): Int = size(root)

    // 检查指定节点的子节点的数量
    private fun size(x: Node?): Int = x?.N ?: 0

    // 检查红黑树是否为空
    fun isEmpty(): Boolean = root == null

    // 检查指定节点的颜色是否为红色
    private fun isRed(x: Node?): Boolean = x != null && x.color == red

    // 节点左旋
    private fun rotateLeft(h: Node): Node {
        val x = h.right
        h.right = x!!.left
        x.left = h
        x.color = h.color
        h.color = red
        x.N = h.N
        h.N = 1 + size(h.left) + size(h.right)
        return x
    }

    // 节点右旋
    private fun rotateRight(h: Node): Node {
        val x = h.left
        h.left = x!!.right
        x.right = h
        x.color = red
        x.N = h.N
        h.N = 1 + size(h.left) + size(h.right)
        return x
    }

    // 转换链接的颜色
    private fun flipColors(h: Node) {
        h.color = red
        h.left!!.color = black
        h.right!!.color = black
    }

    // 向红黑树中添加节点
    fun put(key: K, value: V) {
        root = put(root, key, value)
        root!!.color = black
    }

    // 向红黑树的指定节点下添加新的节点
    private fun put(h: Node?, key: K, value: V): Node {
        var h = h
        when (h) {
            null -> return Node(key, value, 1, red)
            else -> {
                val cmp = key.compareTo(h.key)
                when {
                    cmp < 0 -> h.left = put(h.left, key, value)
                    cmp > 0 -> h.right = put(h.right, key, value)
                    else -> h.value = value
                }

                if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h)
                if (isRed(h.left) && isRed(h.left!!.left)) h = rotateRight(h)
                if (isRed(h.left) && isRed(h.right)) flipColors(h)

                h.N = size(h.left) + size(h.right) + 1
                return h
            }
        }
    }

    // 删除指定的节点
    fun delete(key: K) {
        if (!contains(key)) return
        if (!isRed(root!!.left) && !isRed(root!!.right)) root!!.color = red
        root = delete(root, key)
        if (!isEmpty()) root!!.color = black
    }

    // 在红黑树的指定节点下删除相应的节点
    private fun delete(h: Node?, key: K): Node? {
        var h = h
        if (key < h!!.key) {
            if (!isRed(h.left) && !isRed(h.left!!.left)) {
                h = moveRedLeft(h)
            }
            h.left = delete(h.left, key)
        } else {
            if (isRed(h.left)) h = rotateRight(h)
            if (key.compareTo(h.key) == 0 && (h.right == null)) return null
            if (!isRed(h.right) && !isRed(h.right!!.left)) h = moveRedRight(h)

            if (key.compareTo(h.key) == 0) {
                h.value = get(h.right, min(h.right).key) as V
                h.key = min(h.right).key
                h.right = deleteMin(h.right)
            } else {
                h.right = delete(h.right, key)
            }
        }

        return balance(h)
    }

    // 检查指定的键是否存在
    fun contains(key: K) = get(key) != null

    // 删除红黑树中最小的节点
    fun deleteMin() {
        if (!isRed(root!!.left) && !isRed(root!!.right)) root!!.color = red
        root = deleteMin(root)
        if (!isEmpty()) root!!.color = black
    }

    // 删除指定节点下最小的节点
    private fun deleteMin(h: Node?): Node? {
        var h = h
        if (h!!.left == null) return null
        if (!isRed(h.left) && !isRed(h.left!!.left)) h = moveRedLeft(h)
        h.left = deleteMin(h.left!!)
        return balance(h)
    }

    // 假设节点 h 为红色，h.left 和 h.left.left 都是黑色， 将节点 h.left 或 h.left 的子节点变红
    private fun moveRedLeft(h: Node): Node {
        var h = h
        flipColors(h)
        if (isRed(h.right!!.left)) {
            h.right = rotateRight(h.right!!)
            h = rotateLeft(h)
        }
        return h
    }

    // 假设节点 h 为红色，h.right 和 h.right.right 都是黑色，将 h.right 或 h.right 的子节点变红
    private fun moveRedRight(h: Node): Node {
        var h = h
        flipColors(h)
        if (!isRed(h.left!!.left)) h = rotateRight(h)
        return h
    }

    // 删除红黑树中最大的节点
    fun deleteMax() {
        if (isRed(root!!.left) && !isRed(root!!.right)) root!!.color = red
        root = deleteMax(root)
        if (!isEmpty()) root!!.color = black
    }

    // 删除指定节点下最大的节点
    private fun deleteMax(h: Node?): Node? {
        var h = h
        if (isRed(h!!.left)) h = rotateRight(h)
        if (h.right == null) return null
        if (!isRed(h.right) && !isRed(h.right!!.left)) h = moveRedRight(h)

        h.right = deleteMax(h.right)
        return balance(h)
    }

    // 将指定节点的子树重新平衡
    private fun balance(h: Node?): Node {
        var h = h
        if (isRed(h!!.right)) h = rotateLeft(h)
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h)
        if (isRed(h.left) && isRed(h.left!!.left)) h = rotateRight(h)
        if (isRed(h.left) && isRed(h.right)) flipColors(h)

        h.N = size(h.left) + size(h.right) + 1
        return h
    }

    // 获取红黑树最小元素的键
    fun min(): K = min(root).key

    // 获取指定元素下最小的节点
    private fun min(h: Node?): Node = when {
        h!!.left == null -> h
        else -> max(h.left)
    }

    // 获取红黑树最大元素的键
    fun max(): K = max(root).key

    // 获取指定节点下最大的节点
    private fun max(h: Node?): Node = when {
        h!!.right == null -> h
        else -> max(h.right)
    }

    // 通过指定的键从红黑树中获取相应的值
    fun get(key: K): V? = get(root, key)

    // 从指定节点下根据键获取相应的值
    private fun get(h: Node?, key: K?): V? {
        if (h == null) return null

        val cmp = key!!.compareTo(h.key)
        return when {
            cmp < 0 -> get(h.left, key)
            cmp > 0 -> get(h.right, key)
            else -> h.value
        }
    }

    // 获取二叉树的高度
    private fun height() = height(root)

    // 获取指定节点的二叉树高度
    private fun height(x: Node?): Int {
        if (x == null) return -1
        return 1 + Math.max(height(x.left), height(x.right))
    }

    // 获取二叉树中不大于指定键的最大节点
    fun floor(key: K): K? {
        if (isEmpty()) throw NoSuchElementException("calls floor() with empty symbol table")
        val node = floor(root, key)
        if (node == null) {
            return null
        } else {
            return node.key
        }
    }

    // 查找指定节点下最大的不大于该键的节点
    private fun floor(x: Node?, key: K): Node? {
        if (x == null) return null
        val cmp = key.compareTo(x.key)
        if (cmp == 0) return x
        if (cmp < 0) return floor(x.left, key)
        val node = floor(x.right, key)
        if (node == null) {
            return x
        } else {
            return node
        }
    }

    // 获取二叉树中不小于指定键的最小节点的键
    fun ceiling(key: K): K? {
        if (isEmpty()) throw NoSuchElementException("calls ceiling() with empty symbol table")
        val node = ceiling(root, key)
        if (node == null) {
            return null
        } else {
            return node.key
        }
    }

    // 在指定节点下获取不小于的指定键的最小节点
    private fun ceiling(x: Node?, key: K): Node? {
        if (x == null) return null
        val cmp = key.compareTo(x.key)
        if (cmp == 0) return x
        if (cmp > 0) return ceiling(x.right, key)
        val node = ceiling(x.left, key)
        if (node != null) {
            return node
        } else {
            return x
        }
    }

    // 在二叉树中查找指定大小的子树
    fun select(k: Int): K {
        if (k < 0 || k >= size()) throw IllegalArgumentException("argument to select() is invalid: " + k)
        val node = select(root, k)
        return node.key
    }

    // 在指定节点下查找指定大小的二叉树
    private fun select(x: Node?, k: Int): Node {
        val size = size(x!!.left)
        if (size > k) {
            return select(x.left, k)
        } else if (size < k) {
            return select(x.right, k - size - 1)
        } else {
            return x
        }
    }

    // 获取二叉树中小于指定键的节点数量
    fun rank(key: K?): Int {
        if (key == null) throw IllegalArgumentException("argument to rank() is null")
        return rank(key, root)
    }

    // 在指定节点下查找小于指定键的节点数量
    private fun rank(key: K, node: Node?): Int {
        if (node == null) return 0
        val cmp = key.compareTo(node.key)
        if (cmp < 0) {
            return rank(key, node.left)
        } else if (cmp > 0) {
            return 1 + size(node.left) + rank(key, node.right)
        } else {
            return size(node.left)
        }
    }

    // 查找二叉树中所有的键
    fun keys(): Iterable<K> {
        if (isEmpty()) return Queue<K>()
        return keys(min(), max())
    }

    // 查找指定范围的键
    fun keys(lo: K?, hi: K?): Queue<K> {
        if (lo == null) throw IllegalArgumentException("first argument to keys() is null")
        if (hi == null) throw IllegalArgumentException("second argument to keys() is null")
        val queue = Queue<K>()
        keys(root, queue, lo, hi)
        return queue
    }

    // 在指定节点下将指定范围的键存入队列中
    private fun keys(x: Node?, queue: Queue<K>, lo: K, hi: K) {
        if (x == null) return
        val cmpLo = lo.compareTo(x.key)
        val cmpHi = hi.compareTo(x.key)
        if (cmpLo < 0) keys(x.left, queue, lo, hi)
        if (cmpLo <= 0 && cmpHi >= 0) queue.enqueue(x.key)
        if (cmpHi > 0) keys(x.right, queue, lo, hi)
    }

    // 检查是否是 BST
    private fun isBST(): Boolean = isBST(root, null, null)

    // 在指定节点下检查指定范围的二叉树是否是 BST
    private fun isBST(x: Node?, min: K?, max: K?): Boolean {
        if (x == null) return true
        if (min != null && x.key.compareTo(min) < 0) return false
        if (max != null && x.key.compareTo(max) > 0) return false
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max)
    }

    // 检查二叉树的大小是否稳定
    private fun isSizeConsistent() = isSizeConsistent(root)

    // 在指定节点下检查二叉树的大小是否稳定
    private fun isSizeConsistent(x: Node?): Boolean {
        if (x == null) return true
        if (x.N != size(x.left) + size(x.right) + 1) return false
        return isSizeConsistent(x.left) && isSizeConsistent(x.right)
    }

    // 检查二叉树的顺序是否稳定
    private fun isRankConsistent(): Boolean {
        for (i in 0 until size()) {
            if (i != rank(select(i))) return false
        }
        for (key in keys()) {
            if (key.compareTo(select(rank(key))) != 0) return false
        }
        return true
    }

    // 检查该树是否是 2-3 树
    private fun is23() = is23(root)

    // 在指定的节点下检查二叉树是否是 2-3 树
    private fun is23(x: Node?): Boolean {
        if (x == null) return true
        if (isRed(x.right)) return false
        if (x != root && isRed(x) && isRed(x.left)) return false
        return is23(x.left) && is23(x.right)
    }

    // 检查二叉树是否平衡
    private fun isBalanced(): Boolean {
        var black = 0
        var x = root
        while (x != null) {
            if (!isRed(x)) black++
            x = x.left
        }
        return isBalanced(root, black)
    }

    // 检查指定节点到叶子节点的路径黑色的数量是否一致
    private fun isBalanced(x: Node?, black: Int): Boolean {
        var black = black
        if (x == null) return black == 0
        if (!isRed(x)) black--
        return isBalanced(x.left, black) && isBalanced(x.right, black)
    }
}

// 单元测试
fun main() {
    val st = RedBlackBST<String, Int>()
    for (i in 0 until 10) {
        st.put(i.toString(), i)
    }

    println("size of red black search table = " + st.size())

    for (s in st.keys()) {
        print(s + "\t")
    }
}