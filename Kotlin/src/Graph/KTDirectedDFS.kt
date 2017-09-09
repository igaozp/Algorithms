package Graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.DirectedDFS

/**
 * 有向图的可达性
 *
 * 检测顶点能够到达的其他顶点
 *
 * @author igaozp
 * @since 2017-09-09
 * @version 1.0
 */
class KTDirectedDFS {
    /**
     * 有向图每个顶点的访问情况
     */
    private var marked: Array<Boolean>? = null

    /**
     * 构造方法
     */
    constructor(G: Digraph, s: Int) {
        marked = Array(G.V())
        dfs(G, s)
    }

    /**
     * 构造方法
     */
    constructor(G: Digraph, sources: Iterable<Int>) {
        marked = Array(G.V())
        sources
                .filterNot { marked!![it] }
                .forEach { dfs(G, it) }
    }

    /**
     * 生成函数的辅助函数
     */
    private fun <T> Array(size: Int): Array<T> = Array(size)

    /**
     * 深度优先搜索
     *
     * @param G 有向图
     * @param v 起始顶点
     */
    private fun dfs(G: Digraph, v: Int) {
        marked!![v] = true
        G.adj(v)
                .filterNot { marked!![it] }
                .forEach { dfs(G, it) }
    }

    /**
     * 检查顶点的访问情况
     *
     * @param v 指定的顶点
     * @return `true` 访问过
     *         `false` 未访问过
     */
    fun marked(v: Int): Boolean = marked!![v]

    /**
     * 测试
     *
     * @param args 命令行参数
     */
    fun main(args: Array<String>) {
        val G = Digraph(In(args[0]))

        val sources = Bag<Int>()
        (1 until args.size).forEach { i -> sources.add(args[i].toInt()) }

        val readchable = DirectedDFS(G, sources)

        (0 until G.V())
                .filter { readchable.marked(it) }
                .forEach { StdOut.print("$it ") }
        StdOut.println()
    }
}