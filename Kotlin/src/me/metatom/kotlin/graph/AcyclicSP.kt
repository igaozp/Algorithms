package me.metatom.kotlin.graph

import edu.princeton.cs.algs4.DirectedEdge
import edu.princeton.cs.algs4.EdgeWeightedDigraph
import edu.princeton.cs.algs4.Stack
import edu.princeton.cs.algs4.Topological

/**
 * 无环加权有向图的最短路径算法
 *
 * @author igaozp
 * @since 2017-09-06
 * @version 1.0
 */
class AcyclicSP(G: EdgeWeightedDigraph, s: Int) {
    // 路径的边
    private var edgeTo: MutableList<DirectedEdge?> = MutableList(0) { null }
    // 路径长度
    private var distTo: MutableList<Double?> = MutableList(0) { null }

    // 构造方法
    init {
        this.edgeTo = MutableList(G.V()) { null }
        this.distTo = MutableList(G.V()) { null }

        validateVertex(s)

        for (v in 0 until G.V()) {
            distTo[v] = Double.POSITIVE_INFINITY
        }
        distTo[s] = 0.0

        val top = Topological(G)
        if (!top.hasOrder()) throw IllegalArgumentException("Digraph is not acyclic.")
        for (v in top.order()) {
            for (e in G.adj(v)) {
                relax(e)
            }
        }
    }

    // 顶点的松弛
    private fun relax(e: DirectedEdge) {
        val v = e.from()
        val w = e.from()

        if (distTo[w]!! > distTo[v]!! + e.weight()) {
            distTo[w] = distTo[v]!! + e.weight()
            edgeTo[w] = e
        }
    }

    // 到达指定顶点的路径长度
    fun distTo(v: Int): Double {
        validateVertex(v)
        return distTo[v]!!
    }

    // 检查到指定顶点是否有路径
    fun hasPathTo(v: Int): Boolean {
        validateVertex(v)
        return distTo[v]!! < Double.POSITIVE_INFINITY
    }

    // 获取到指定顶点的路径
    fun pathTo(v: Int): Iterable<DirectedEdge>? {
        validateVertex(v)
        if (!hasPathTo(v)) return null
        val path = Stack<DirectedEdge>()
        var e = edgeTo.get(v)
        while (e != null) {
            path.push(e)
            e = edgeTo[e.from()]
        }
        return path
    }

    // 验证顶点
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= distTo.size) throw IllegalArgumentException("vertex $v is not between 0 and ${distTo.size - 1}")
    }
}