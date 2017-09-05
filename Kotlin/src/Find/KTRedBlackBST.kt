package Find

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
class KTRedBlackBST<K : Comparable<K>, V> {
    /**
     * 红黑树节点颜色，使用 true 代表红色，false 代表黑色
     */
    private val RED = true
    private val BLACK = false

    /**
     * 红黑树的根节点
     */
    private var root: Node? = null

    /**
     * 用于红黑树的内部节点
     */
    inner class Node(var key: K, var value: V, var N: Int, var color: Boolean) {
        var left: Node? = null
        var right: Node? = null
    }

    /**
     * 检查红黑树的节点数量
     *
     * @return 红黑树的节点数量
     */
    fun size(): Int = size(root)

    /**
     * 检查指定节点的子节点的数量
     *
     * @param x 需要检查的节点
     * @return 节点数量
     */
    private fun size(x: Node?): Int = x?.N ?: 0

    /**
     * 检查红黑树是否为空
     *
     * @return `true` 红黑树为空
     *         `false` 红黑树不为空
     */
    fun isEmpty(): Boolean = root == null

    /**
     * 检查指定节点的颜色是否为红色
     *
     * @param x 需要检查的节点
     * @return `true` 节点为红色
     *         `false` 节点为黑色
     */
    private fun isRed(x: Node?): Boolean = x != null && x.color == RED

    /**
     * 节点左旋
     *
     * @param h 需要旋转的节点
     * @return 旋转后的节点
     */
    fun rotateLeft(h: Node): Node {
        val x = h.right
        h.right = x!!.left
        x.left = h
        x.color = h.color
        h.color = RED
        x.N = h.N
        h.N = 1 + size(h.left) + size(h.right)
        return x
    }

    /**
     * 节点右旋
     *
     * @param h 需要旋转的节点
     * @return 旋转后的节点
     */
    fun rotateRight(h: Node): Node {
        val x = h.left
        h.left = x!!.right
        x.right = h
        x.color = RED
        x.N = h.N
        h.N = 1 + size(h.left) + size(h.right)
        return x
    }

    /**
     * 转换链接的颜色
     *
     * @param h 需要转换的节点
     */
    fun flipColors(h: Node) {
        h.color = RED
        h.left!!.color = BLACK
        h.right!!.color = BLACK
    }

    /**
     * 向红黑树中添加节点
     *
     * @param key 添加的节点的键
     * @param val 添加的节点的值
     */
    fun put(key: K, value: V) {
        root = put(root, key, value)
        root!!.color = BLACK
    }

    /**
     * 向红黑树的指定节点下添加新的节点
     *
     * @param h 指定的节点
     * @param key 添加节点的键
     * @param value 添加节点的值
     * @return 添加节点的指定节点
     */
    private fun put(h: Node?, key: K, value: V): Node {
        var h = h
        when (h) {
            null -> return Node(key, value, 1, RED)
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

    /**
     * 在红黑树的指定节点下删除相应的节点
     *
     * @param h 指定的节点
     * @param key 删除节点的键
     * @return 删除节点的指定节点
     */
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

    /**
     * 删除红黑树中最小的节点
     */
    fun deleteMin() {
        if (!isRed(root!!.left) && !isRed(root!!.right)) root!!.color = RED
        root = deleteMin(root)
        if (!isEmpty()) root!!.color = BLACK
    }

    /**
     * 删除指定节点下最小的节点
     *
     * @param h 指定的节点
     * @return 删除节点的指定节点
     */
    private fun deleteMin(h: Node?): Node? {
        var h = h
        if (h!!.left == null) return null
        if (!isRed(h.left) && !isRed(h.left!!.left)) h = moveRedLeft(h)
        h.left = deleteMin(h.left!!)
        return balance(h)
    }

    /**
     * 假设节点 h 为红色，h.left 和 h.left.left 都是黑色，
     * 将节点 h.left 或 h.left 的子节点变红
     *
     * @param h 指定的节点
     * @return 转换后指定的节点
     */
    private fun moveRedLeft(h: Node): Node {
        var h = h
        flipColors(h)
        if (isRed(h.right!!.left)) {
            h.right = rotateRight(h.right!!)
            h = rotateLeft(h)
        }
        return h
    }

    /**
     * 假设节点 h 为红色，h.right 和 h.right.right 都是黑色，
     * 将 h.right 或 h.right 的子节点变红
     *
     * @param h 指定的节点
     * @return 转换后指定的节点
     */
    private fun moveRedRight(h: Node): Node {
        var h = h
        flipColors(h)
        if (!isRed(h.left!!.left)) h = rotateRight(h)
        return h
    }

    /**
     * 删除红黑树中最大的节点
     */
    fun deleteMax() {
        if (isRed(root!!.left) && !isRed(root!!.right)) root!!.color = RED
        root = deleteMax(root)
        if (!isEmpty()) root!!.color = BLACK
    }

    /**
     * 删除指定节点下最大的节点
     *
     * @param h 指定的节点
     * @return 删除后指定的节点
     */
    private fun deleteMax(h: Node?): Node? {
        var h = h
        if (isRed(h!!.left)) h = rotateRight(h)
        if (h.right == null) return null
        if (!isRed(h.right) && !isRed(h.right!!.left)) h = moveRedRight(h)

        h.right = deleteMax(h.right)
        return balance(h)
    }

    /**
     * 将指定节点的子树重新平衡
     *
     * @param h 需要平衡的节点
     * @return 平衡后的节点
     */
    private fun balance(h: Node?): Node {
        var h = h
        if (isRed(h!!.right)) h = rotateLeft(h)
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h)
        if (isRed(h.left) && isRed(h.left!!.left)) h = rotateRight(h)
        if (isRed(h.left) && isRed(h.right)) flipColors(h)

        h.N = size(h.left) + size(h.right) + 1
        return h
    }

    /**
     * 获取红黑树最小元素的键
     *
     * @return 最小元素的键
     */
    fun min(): K = min(root).key

    /**
     * 获取指定元素下最小的节点
     *
     * @param h 指定的元素
     * @return 指定元素的最小节点
     */
    private fun min(h: Node?): Node = when {
        h!!.right == null -> h
        else -> max(h.right)
    }

    /**
     * 获取红黑树最大元素的键
     *
     * @return 最大元素的键
     */
    fun max(): K = max(root).key

    /**
     * 获取指定节点下最大的节点
     *
     * @param h 指定的节点
     * @return 指定元素的最大节点
     */
    private fun max(h: Node?): Node = when {
        h!!.right == null -> h
        else -> max(h.right)
    }

    /**
     * 通过指定的键从红黑树中获取相应的值
     *
     * @param key 需要获取值的键
     * @return 相应键的值
     */
    fun get(key: K): V? = get(root, key)

    /**
     * 从指定节点下根据键获取相应的值
     *
     * @param h 指定的节点
     * @param key 需要获取值的键
     * @return 相应键的值
     */
    private fun get(h: Node?, key: K?): V? {
        if (h == null) return null

        val cmp = key!!.compareTo(h.key)
        return when {
            cmp < 0 -> get(h.left, key)
            cmp > 0 -> get(h.right, key)
            else -> h.value
        }
    }
}