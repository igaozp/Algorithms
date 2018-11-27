package me.metatom.kotlin.string

import edu.princeton.cs.algs4.StdOut

/**
 * KMP 字符串查找算法
 *
 * @author igaozp
 * @since 2017-09-13
 * @version 1.0
 */
class KMP(pat: String) {
    /**
     * 匹配的字符串
     */
    private var pat: String? = pat
    /**
     * 有限状态自动机
     */
    private var dfa: MutableList<IntArray>? = null

    /**
     * KMP 构造方法
     */
    init {
        val M = pat.length
        val R = 256
        dfa = MutableList(R) {IntArray(M)}
        dfa!![pat[0].toInt()][0] = 1
        var X = 0
        var j = 1
        while (j < M) {
            for (c in 0 until R) {
                dfa!![c][j] = dfa!![c][X]
            }
            dfa!![pat[j].toInt()][j] = j + 1
            X = dfa!![pat[j].toInt()][X]
            j++
        }
    }

    /**
     * KMP 查找
     *
     * @param txt 用作搜索的文本
     * @return 查找到的字符串下标
     */
    fun search(txt: String): Int {
        val N = txt.length
        val M = pat!!.length
        var i = 0
        var j = 0

        while (i < N && j < M) {
            j = dfa!![txt[i].toInt()][j]
            i++
        }
        if (j == M) {
            return i - M
        } else {
            return N
        }
    }

    /**
     * 测试
     *
     * @param args 命令行参数
     */
    fun main(args: Array<String>) {
        val pat = args[0]
        val txt = args[1]
        val kmp = KMP(pat)

        StdOut.println("text:   $txt")
        val offset = kmp.search(txt)
        StdOut.print("pattern: ")

        for (i in 0 until offset) {
            StdOut.print(" ")
        }
        StdOut.println(pat)
    }
}