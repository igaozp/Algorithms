package me.metatom.kotlin.graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.DepthFirstOrder
import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.DirectedCycle
import edu.princeton.cs.algs4.EdgeWeightedDigraph
import edu.princeton.cs.algs4.Topological

/**
 * 拓扑排序
 *
 * @author igaozp
 * @since 2017-09-11
 * @version 1.0
 */
class Topological {
    // 拓扑序列
    private var order: Iterable<Int>? = null
    // rank[v] = rank of vertex v in order
    private var rank = MutableList(0) { 0 }

    /**
     * 拓扑排序的构造方法
     *
     * @param G 有向图
     */
    constructor(G: Digraph) {
        val cycleFinder = DirectedCycle(G)

        // 检查是否有环，若没有则进行拓扑排序
        if (!cycleFinder.hasCycle()) {
            val dfs = DepthFirstOrder(G)
            order = dfs.reversePost()
            var i = 0
            for (v in order!!) {
                rank[v] = i++
            }
        }
    }

    /**
     * 构造方法
     *
     * @param G 带权重的有向图
     */
    constructor(G: EdgeWeightedDigraph) {
        val finder = EdgeWeightedDirectedCycle(G)
        if (!finder.hasCycle()) {
            val dfs = DepthFirstOrder(G)
            order = dfs.reversePost()
        }
    }

    /**
     * 获取拓扑序列
     *
     * @return 拓扑序列
     */
    fun order(): Iterable<Int>? = if (order != null) order else null

    /**
     * 检查是否是有向无环图
     *
     * @return `true` 是有向无环图
     *         `false` 不是有向无环图
     */
    fun isDAG(): Boolean = order != null

    /**
     * 验证顶点
     *
     * @param v 需要验证的顶点
     */
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= rank.size) {
            throw IllegalArgumentException("vertex $v is not between 0 and ${rank.size - 1}")
        }
    }
}

/**
 *  单元测试
 */
fun main(args: Array<String>) {
    val filename = args[0]
    val separator = args[0]
    val sg = SymbolDigraph(filename, separator)
    val top = Topological(sg.G())

    for (v in top.order()) {
        StdOut.println(sg.name(v))
    }
}