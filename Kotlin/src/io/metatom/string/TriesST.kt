package io.metatom.string

import edu.princeton.cs.algs4.Queue


/**
 * 基于单词查找树的符号表
 *
 * @author igaozp
 * @since 2017-09-16
 * @version 1.0
 *
 * @param <Value> 泛型类型
 */
class TriesST<Value> {
    /**
     * 基数
     */
    private var R: Int = 256
    /**
     * 根节点
     */
    private var root: Node? = null

    /**
     * 内部定义的节点类
     */
    inner class Node {
        internal var value: Value? = null

        internal var next: Array<Node?> = arrayOfNulls(R)
    }

    /**
     * 获取键获取值
     *
     * @param key 指定的键
     * @return 值
     */
    fun get(key: String): Value? {
        val x = get(root, key, 0) ?: return null
        return x.value
    }

    /**
     * 查找指定节点下与 key 相关联的值
     *
     * @param x 指定的节点
     * @param key 查找的键
     * @param d 用于选择单词树的分支，标记已选字符串长度
     * @return 相应的节点
     */
    fun get(x: Node?, key: String, d: Int): Node? {
        if (x == null) return null

        // 标记的长度和查找长度相同
        if (d == key.length) return x

        // 获取下一个字符的分支
        val c: Char = key[d]

        return get(x.next[c.toInt()], key, d + 1)
    }

    /**
     * 向单词树中添加新的字符串和相应的值
     *
     * @param key 用作查找符号的字符串
     * @param value 字符串对应的值
     */
    fun put(key: String, value: Value) {
        root = put(root, key, value, 0)
    }

    /**
     * 向指定节点下添加新的字符串和对应的值
     *
     * @param x 指定的节点
     * @param key 用作查找符号的字符串
     * @param value 字符串对应的值
     * @param d 用于选择单词树的分支，标记已选字符串长度
     * @return 插入后指定的节点
     */
    private fun put(x: Node?, key: String, value: Value, d: Int): Node {
        var x = x
        if (x == null) x = Node()
        if (d == key.length) {
            x.value = value
            return x
        }
        val c: Char = key[d]
        x.next[c.toInt()] = put(x.next[c.toInt()], key, value, d + 1)
        return x
    }

    /**
     * 获取符号表中所有的键
     *
     * @return 所有的键
     */
    fun keys(): Iterable<String> = keysWithPrefix("")

    /**
     * 查找指定前缀的键的集合
     *
     * @param pre 指定的前缀
     * @return 键的集合
     */
    fun keysWithPrefix(pre: String): Iterable<String> {
        val q = Queue<String>()
        collect(get(root, pre, 0), pre, q)
        return q
    }

    /**
     * 在指定节点下获取指定前缀的键的集合
     *
     * @param x 指定的节点
     * @param pre 指定的前缀
     * @param q 存储键的队列
     */
    private fun collect(x: Node?, pre: String, q: Queue<String>) {
        if (x == null) return

        if (x.value != null) {
            q.enqueue(pre)
        }
        for (c in 0 until R) {
            collect(x.next[c], pre + c, q)
        }
    }

    /**
     * 在符号表中查找相匹配的键的集合
     *
     * @param pat 匹配的字符串
     * @return 键的集合
     */
    fun keysThatMatch(pat: String): Iterable<String> {
        val q = Queue<String>()
        collect(root, "", pat, q)
        return q
    }

    /**
     * 在指定的节点和前缀下，对指定的字符串进行匹配
     *
     * @param x 指定的节点
     * @param pre 指定的前缀
     * @param pat 匹配的字符串
     * @param q 存储匹配的字符串
     */
    private fun collect(x: Node?, pre: String, pat: String, q: Queue<String>) {
        val d = pre.length

        if (x == null) return

        if (d == pat.length && x.value != null) {
            q.enqueue(pre)
        }
        if (d == pat.length) return

        val next: Char = pat[d]
        (0 until R).forEach { c ->
            if (next == '.' || next.toInt() == c) {
                collect(x.next[c], pre + c, pat, q)
            }
        }
    }

    /**
     * 查找指定字符串的最长前缀
     *
     * @param s 指定的字符串
     * @return 最长的前缀
     */
    fun longestPrefixOf(s: String): String {
        val length = search(root, s, 0, 0)
        return s.substring(0, length)
    }

    /**
     * 在指定节点下查找指定字符串的最长前缀
     *
     * @param x 指定的节点
     * @param s 指定的字符串
     * @param d 用于选择单词树的分支，标记已选字符串长度
     * @param length 前缀的长度
     * @return 最长前缀下标
     */
    private fun search(x: Node?, s: String, d: Int, length: Int): Int {
        var length = length
        if (x == null) return length
        if (x.value != null) length = d
        if (d == s.length) return length

        val c = s[d]
        return search(x.next[c.toInt()], s, d + 1, length)
    }

    /**
     * 删除指定的字符串
     *
     * @param key 指定的字符串
     */
    fun delete(key: String) {
        root = delete(root, key, 0)
    }

    /**
     * 在指定的节点下删除指定的字符串
     *
     * @param x 指定的节点
     * @param key 指定的字符串
     * @param d 用于选择单词树的分支，标记已选字符串长度
     * @return 删除后指定的节点
     */
    private fun delete(x: Node?, key: String, d: Int): Node? {
        if (x == null) return null
        if (d == key.length) {
            x.value = null
        } else {
            val c = key[d]
            x.next[c.toInt()] = delete(x.next[c.toInt()], key, d + 1)
        }
        if (x.value != null) return x
        (0 until R).forEach { c ->
            if (x.next[c] != null) {
                return x
            }
        }
        return null
    }
}