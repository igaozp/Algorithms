package me.metatom.kotlin.string

import edu.princeton.cs.algs4.Bag
import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.DirectedDFS
import edu.princeton.cs.algs4.Stack

/**
 * 正则表达式的模式匹配
 *
 * @author igaozp
 * @since 2017-07-28
 * @version 1.0
 */
class NFA(regexp: String) {
    /**
     * 匹配转换
     */
    private var re: Array<Char?>? = null
    /**
     * epsilon 转换
     */
    private var G: Digraph? = null
    /**
     * 状态数量
     */
    private var M: Int = 0

    /**
     * 构造方法，构建正则表达式的 NFA
     */
    init {
        val ops = Stack<Int>()
        val re = regexp.toCharArray()
        M = re.size
        G = Digraph(M + 1)
        for (i in 0 until M) {
            var  lp = i
            if (re[i] == '(' || re[i] == '|') {
                ops.push(i)
            } else if (re[i] == ')') {
                val or = ops.pop()
                if (re[or] == '|') {
                    lp = ops.pop()
                    G!!.addEdge(lp, or + 1)
                    G!!.addEdge(or, i)
                } else {
                    lp = or
                }
            }

            if (i < M - 1 && re[i + 1] == '*') {
                G!!.addEdge(lp, i + 1)
                G!!.addEdge(i + 1, lp)
            }

            if (re[i] == '(' || re[i] == '*' || re[i] == ')') {
                G!!.addEdge(i, i + 1)
            }
        }
    }

    /**
     * NFA 能否检查文本
     *
     * @param txt 检查的文本
     * @return `true` 能够检查
     *         `false` 不能检查
     */
    fun recognizes(txt: String): Boolean {
        var pc = Bag<Int>()
        var dfs = DirectedDFS(G, 0)

        (0 .. G!!.V()).forEach { v ->
            if (dfs.marked(v)) {
                pc.add(v)
            }
        }

        for (i in 0 until txt.length) {
            val match = Bag<Int>()
            pc.forEach { v ->
                if (v < M) {
                    if (re!![v] == txt[i] || re!![v] == '.') {
                        match.add(v + 1)
                    }
                }
            }

            pc = Bag()
            dfs = DirectedDFS(G, match)

            (0 until G!!.V()).forEach { v ->
                if (dfs.marked(v)) {
                    pc.add(v)
                }
            }
        }

        pc.forEach { v ->
            if (v == M) {
                return true
            }
        }

        return false
    }
}