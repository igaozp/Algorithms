package String

import edu.princeton.cs.algs4.StdOut

/**
 * Boyer-Moore 字符串匹配算法
 *
 * @author igaozp
 * @since 2017-09-12
 * @version 1.0
 */
class KTBoyerMoore(pat: String) {
    /**
     * 记录每个字符在模式中出现的最靠右的地方
     */
    private var right: MutableList<Int?>? = null
    /**
     * 匹配的字符串（模式）
     */
    private var pat: String? = pat

    /**
     * 构造方法
     */
    init {
        val M = pat.length
        val R = 256
        right = MutableList(R, { null })

        for (c in 0 until R) {
            right!![c] = -1
        }

        for (j in 0 until M) {
            right!![pat[j].toInt()] = j
        }
    }

    /**
     * 字符串查找
     *
     * @param txt 用于查找字符串的文本
     * @return 查找到的字符串下标
     */
    fun search(txt: String): Int {
        val N = txt.length
        val M = pat!!.length
        var skip = 0
        var i = 0
        while (i <= N - M) {0
            skip = 0
            for (j in (M - 1) downTo 0) {
                if (pat!![j] != txt[i + j]) {
                    skip = j - right?.get(txt[i + j].toInt())!!
                    if (skip < 1) {
                        skip = 1
                    }
                    break
                }
            }
            if (skip == 0) {
                return i
            }
            i += skip
        }
        return N
    }

    /**
     * 测试
     *
     * @param args 命令行参数
     */
    fun main(args: Array<String>) {
        val pat = args[0]
        val txt = args[1]
        val boyerMoore = BoyerMoore(pat)

        StdOut.println("text:   $txt")
        val offset = boyerMoore.search(txt)
        StdOut.print("pattern: ")

        for (i in 0 until offset) {
            StdOut.print(" ")
        }
        StdOut.println(pat)
    }
}