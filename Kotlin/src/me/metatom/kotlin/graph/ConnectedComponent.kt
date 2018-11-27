package me.metatom.kotlin.graph

import edu.princeton.cs.algs4.EdgeWeightedGraph
import edu.princeton.cs.algs4.Graph
import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.Queue

/**
 * 使用深度优先搜索查找图中的连通分量
 *
 * @author igaozp
 * @since 2017-09-08
 * @version 1.0
 */
class ConnectedComponent {
    // 存储图中顶点的访问状态
    private var marked = MutableList(0) { false }
    // 连通分量的标识符
    private var id = MutableList(0) { 0 }
    // 存放给定分量的顶点数量
    private var size = MutableList(0) { 0 }
    // 顶点的访问次数
    private var count: Int = 0

    constructor(G: Graph) {
        marked = MutableList(G.V()) { false }
        id = MutableList(G.V()) { 0 }
        size = MutableList(G.V()) { 0 }

        for (s in 0 until G.V()) {
            if (!marked[s]) {
                dfs(G, s)
                count++
            }
        }
    }

    constructor(G: EdgeWeightedGraph) {
        marked = MutableList(G.V()) { false }
        id = MutableList(G.V()) { 0 }
        size = MutableList(G.V()) { 0 }

        for (v in 0 until G.V()) {
            if (!marked[v]) {
                dfs(G, v)
                count++
            }
        }
    }

    // 深度优先搜索
    private fun dfs(G: Graph, v: Int) {
        marked[v] = true
        id[v] = count
        size[count]++

        // 访问与 v 连通的顶点
        G.adj(v)
                .filterNot { marked[it] }
                .forEach { dfs(G, it) }
    }

    // 深度优先搜索
    private fun dfs(G: EdgeWeightedGraph, v: Int) {
        marked[v] = true
        id[v] = count
        size[count]++

        // 访问与 v 连通的顶点
        G.adj(v)
                .filterNot { marked[it.other(v)] }
                .forEach { dfs(G, it.other(v)) }
    }

    // 检查两个连通分量是否连通
    fun connected(v: Int, w: Int): Boolean {
        validateVertex(v)
        validateVertex(w)
        return id[v] == id[w]
    }

    // 检查某个顶点的连通分量的标识符
    fun id(v: Int): Int {
        validateVertex(v)
        return id[v]
    }

    // 图的访问次数
    fun count(): Int = count

    // 获取指定顶点的联通顶点数量
    fun size(v: Int): Int {
        validateVertex(v)
        return size[id[v]]
    }

    // 验证顶点
    fun validateVertex(v: Int) {
        val V = marked.size
        if (v < 0 || v >= V) {
            throw IllegalArgumentException("vertex $v is not between 0 and ${V - 1}")
        }
    }
}

// 单元测试
fun main(args: Array<String>) {
    val input = In(args[0])
    val G = Graph(input)
    val cc = ConnectedComponent(G)

    println("${cc.count()} components")

    val components = MutableList(cc.count(), { Queue<Int>() })

    for (v in 0 until G.V()) {
        components[cc.id(v)].enqueue(v)
    }

    for (i in 0 until cc.count()) {
        components[i].forEach { println("$it ") }
        println()
    }
}