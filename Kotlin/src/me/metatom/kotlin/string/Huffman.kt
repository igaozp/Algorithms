package me.metatom.kotlin.string

import edu.princeton.cs.algs4.BinaryStdIn
import edu.princeton.cs.algs4.BinaryStdOut
import edu.princeton.cs.algs4.MinPQ

/**
 * 哈夫曼压缩
 *
 * @author igaozp
 * @since 2017-09-12
 * @version 1.0
 */
class Huffman {
    /**
     * ASCII 字母表
     */
    private var R: Int = 256

    /**
     * 内部定义的单词查找树节点类
     */
    inner class Node(ch: Char, freq: Int, var left: Node?, var right: Node?) : Comparable<Node> {
        /**
         * 被编码的字符
         */
        internal var ch: Char? = ch
        /**
         * 字符出现的次数
         */
        internal var freq: Int? = freq

        /**
         * 检查该节点是否是叶子节点
         *
         * @return `true` 是叶子节点
         *         `false` 不是叶子节点
         */
        fun isLeaf(): Boolean = left == null && right == null

        /**
         * 与其他节点对比出现的次数
         *
         * @param other 其他的节点
         * @return 出现次数之差
         */
        override fun compareTo(other: Node): Int = this.freq!! - other.freq!!
    }

    /**
     * 解码
     */
    fun expand() {
        val root = readTrie()
        val N = BinaryStdIn.readInt()

        for (i in 0 until N) {
            var x: Node? = root
            while (!x!!.isLeaf()) {
                x = when {
                    BinaryStdIn.readBoolean() -> x.right
                    else -> x.left
                }
            }
            BinaryStdOut.write(x.ch!!)
        }
        BinaryStdOut.close()
    }

    /**
     * 压缩
     */
    fun compress() {
        val s = BinaryStdIn.readString()
        val input = s.toCharArray()
        val freq = Array<Int>(R)

        for (c in input) {
            freq[c.toInt()]++
        }

        val root = buildTrie(freq)
        val st = Array<String>(R)
        buildCode(st, root, "")
        writeTrie(root)
        BinaryStdOut.write(input.size)

        for (c in input) {
            val code = st[c.toInt()]
            for (j in 0 until code.length) {
                if ("1" == code[j].toString()) {
                    BinaryStdOut.write(true)
                } else {
                    BinaryStdOut.write(false)
                }
            }
            BinaryStdOut.close()
        }
    }

    /**
     * 使用单词查找树构造编译表
     *
     * @param root 单词查找树的根节点
     * @return 构造完成的编译表
     */
    private fun buildCode(root: Node): Array<String> {
        val st = Array<String>(R)
        buildCode(st, root, "")
        return st
    }

    /**
     * 使用单词查找树构造编译表
     *
     * @param st 编译表
     * @param x 开始的节点
     * @param s 初始编码
     */
    private fun buildCode(st: Array<String>, x: Node, s: String) {
        if (x.isLeaf()) {
            st[x.ch as Int] = s
            return
        }
        buildCode(st, x.left!!, s + '0')
        buildCode(st, x.right!!, s + '1')
    }

    /**
     * 构造哈夫曼编码的查找树
     *
     * @param freq 字符出现次数表
     * @return 树中的最小节点
     */
    private fun buildTrie(freq: Array<Int>): Node {
        val pq = MinPQ<Node>()

        (0 until R).forEach { c ->
            if (freq[c] > 0) {
                pq.insert(Node(c.toChar(), freq[c], null, null))
            }
        }

        while (pq.size() > 1) {
            val x = pq.delMin()
            val y = pq.delMin()
            val parent = Node('\u0000', x.freq!!.plus(y.freq!!), x, y)
            pq.insert(parent)
        }

        return pq.delMin()
    }

    /**
     * 将查找树写为比特字符串
     *
     * @param x 开始节点
     */
    private fun writeTrie(x: Node) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true)
            BinaryStdOut.write(x.ch!!)

            return
        }

        BinaryStdOut.write(false)

        writeTrie(x.left!!)
        writeTrie(x.right!!)
    }

    /**
     * 从比特流的先序表示中重建单词查找树
     *
     * @return 查找树的根节点
     */
    private fun readTrie(): Node {
        if (BinaryStdIn.readBoolean()) {
            return Node(BinaryStdIn.readChar(), 0, null, null)
        }
        return Node('\u0000', 0, readTrie(), readTrie())
    }

    /**
     * 生成数组用的辅助方法
     */
    private fun <T> Array(size: Int): Array<T> = Array(size)
}