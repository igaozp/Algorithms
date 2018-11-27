package me.metatom.kotlin.graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.DepthFirstOrder
import edu.princeton.cs.algs4.Digraph

/**
 * 计算强连通分量的 Kosaraju 算法
 *
 * @author igaozp
 * @since 2017-09-10
 * @version 1.0
 */
class KosarajuSCC {
    // 存储顶点的访问情况
    private var marked = MutableList(0) { false }
    // 强连通分量的标识符
    private var id = MutableList(0) { 0 }
    // 强连通分量的数量
    private var count: Int = 0

    /**
     * 构造方法
     *
     * @param G 有向图
     */
    constructor(G: Digraph) {
        marked = MutableList(G.V()) { false }
        id = MutableList(G.V()) { 0 }
        val order = DepthFirstOrder(G.reverse())

        for (s in order.reversePost()) {
            if (!marked[s]) {
                dfs(G, s)
                count++
            }
        }

        assert(check(G))
    }

    /**
     * 深度优先搜索
     *
     * @param G 有向图
     * @param v 搜索的起始顶点
     */
    private fun dfs(G: Digraph, v: Int) {
        marked[v] = true
        id[v] = count

        G.adj(v).forEach { w ->
            if (!marked[w]) dfs(G, w)
        }
    }

    /**
     * 检测连个顶点是否是强连通分量
     *
     * @param v 有向图的顶点
     * @param w 有向图的顶点
     * @return `true` 是强连通分量
     *         `false` 不是强连通分量
     */
    fun stronglyConnected(v: Int, w: Int): Boolean {
        validateVertex(v)
        validateVertex(w)
        return id[v] == id[w]
    }

    /**
     * 获取指定顶点的标识符
     *
     * @param v 指定的顶点
     * @return 标识符
     */
    fun id(v: Int): Int {
        validateVertex(v)
        return id[v]
    }

    /**
     * 获取强连通分量的数量
     *
     * @return 强连通分量的数量
     */
    fun count(): Int = count

    /**
     * 检查有向图
     *
     * @param G 需要检查的有向图
     * @return 检查结果
     */
    private fun check(G: Digraph): Boolean {
        val tc = TransitiveClosure(G)
        for (v in 0 until G.V()) {
            for (w in 0 until G.V()) {
                if (stronglyConnected(v, w) != (tc.reachable(v, w)) && tc.reachable(w, v)) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * 验证顶点
     *
     * @param v 需要验证的顶点
     */
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= marked.size) {
            throw IllegalArgumentException("vertex $v is not between 0 and ${marked.size + 1}")
        }
    }
}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = Digraph(input)
    val scc = KosarajuSCC(graph)

    val m = scc.count()
    println("$m strong components")

    val components = MutableList(m) { Queue<Int>() }
    for (v in 0 until graph.V()) {
        components[scc.id(v)].enqueue(v)
    }

    for (i in 0 until m) {
        for (v in components[i]) {
            println("$v ")
        }
        println()
    }
}