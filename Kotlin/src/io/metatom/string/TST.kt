package io.metatom.string

/**
 * 基于三向单词查找树的符号表
 *
 * @author igaozp
 * @since 2017-09-16
 * @version 1.0
 *
 * @param <Value> 泛型类型
 */
class TST<Value> {
    /**
     * 根节点
     */
    private var root: Node? = null

    /**
     * 内部定义的节点类
     */
    inner class Node {
        // 字符
        internal var c: Char? = null
        // 左中右子树
        internal var left: Node? = null
        internal var mid: Node? = null
        internal var right: Node? = null
        // 相关联的值
        internal var value: Value? = null
    }

    /**
     * 根据字符串查找相关联的值
     *
     * @param key 用字符串表示的键
     * @return 相关联的值
     */
    fun get(key: String): Value? {
        val x = get(root, key, 0) ?: return null
        return x.value
    }

    /**
     * 在指定节点下查找字符串相关联的节点
     *
     * @param x 指定的节点
     * @param key 用字符串表示的键
     * @param d 记录查找字符串的字符位置
     * @return 查找到的节点
     */
    private fun get(x: Node?, key: String, d: Int): Node? {
        if (x == null) return null

        val c = key[d]

        return when {
            c < x.c!! -> get(x.left, key, d)
            c > x.c!! -> get(x.right, key, d)
            d < key.length - 1 -> get(x.mid, key, d + 1)
            else -> x
        }
    }

    /**
     * 向树中添加新的键值对
     *
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Value) {
        root = put(root, key, value, 0)
    }

    /**
     * 在指定节点下添加键值对
     *
     * @param x 指定的节点
     * @param key 键
     * @param value 值
     * @param d 记录查找字符串的字符位置
     * @return 添加后的指定节点
     */
    private fun put(x: Node?, key: String, value: Value, d: Int): Node {
        val c = key[d]
        var x = x

        if (x == null) {
            x = Node()
            x.c = c
        }

        when {
            c < x.c!! -> x.left = put(x.left, key, value, d)
            c > x.c!! -> x.right = put(x.right, key, value, d)
            d < key.length - 1 -> x.mid = put(x.mid, key, value, d + 1)
            else -> x.value = value
        }
        return x
    }
}