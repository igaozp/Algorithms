package me.metatom.kotlin.graph

import edu.princeton.cs.algs4.Graph
import edu.princeton.cs.algs4.In
import me.metatom.java.base.Stack

/**
 * 使用深度优先搜索查找图中的路径
 *
 * @author igaozp
 * @since 2017-09-08
 * @version 1.0
 */
class DepthFirstPaths(G: Graph, s: Int) {
    // 存储顶点的访问情况
    private var marked = MutableList(0) { false }
    // 一棵由父链接标识的树，从起点到一个顶点的已知路径上的最后一个顶点
    private var edgeTo = MutableList(0) { 0 }
    // 起点
    private var s = -1

    /**
     * 构造方法
     */
    init {
        marked = MutableList(G.V()) { false }
        edgeTo = MutableList(G.V()) { 0 }
        this.s = s
        validateVertex(s)
        dfs(G, s)
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜索的图
     * @param v 搜索的起始顶点
     */
    fun dfs(G: Graph, v: Int) {
        marked[v] = true
        for (w in G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v
                dfs(G, w)
            }
        }
    }

    /**
     * 检查指定顶点是否由相应的路径
     *
     * @param v 指定的顶点
     * @return `true` 有路径
     *         `false` 没有路径
     */
    fun hasPathTo(v: Int): Boolean {
        validateVertex(v)
        return marked[v]
    }

    /**
     * 到指定顶点的搜索路径
     *
     * @param v 指定的顶点
     * @return 路径
     */
    fun pathTo(v: Int): Iterable<Int> {
        val path = Stack<Int>()
        if (!hasPathTo(v)) return path
        var x = v
        while (x != s) {
            path.push(x)
            x = edgeTo[x]
        }
        path.push(s)
        return path
    }

    /**
     * 验证顶点的正确性
     *
     * @param v 需要验证的节点
     */
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= marked.size) {
            throw IllegalArgumentException("vertex $v is not between 0 and ${marked.size - 1}")
        }
    }
}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = Graph(input)
    val s = args[1].toInt()
    val dfs = DepthFirstPaths(graph, s)

    for (v in 0 until graph.V()) {
        if (dfs.hasPathTo(v)) {
            print("$s to $v: ")
            for (x in dfs.pathTo(v)) {
                if (x == s) {
                    print(x)
                } else {
                    print("-$x")
                }
            }
            println()
        } else {
            println("$s to $v: not connected")
        }
    }
}