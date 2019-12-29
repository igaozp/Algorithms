package io.metatom.graph

import edu.princeton.cs.algs4.DirectedEdge
import edu.princeton.cs.algs4.EdgeWeightedDigraph
import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.IndexMinPQ
import java.util.*

/**
 * 最短路径的 Dijkstra 算法
 *
 * @author igaozp
 * @since 2017-09-09
 * @version 1.0
 */
class DijkstraSP(G: EdgeWeightedDigraph, s: Int) {
    // 距离路径最近的边
    private var edgeTo: MutableList<DirectedEdge?> = MutableList(0) { null }
    // 边的权重
    private var distTo = MutableList(0) { 0.0 }
    // 有效的横切边
    private var pq = IndexMinPQ<Double>(0)

    /**
     * 构造方法
     */
    init {
        G.edges().forEach {
            if (it.weight() < 0) {
                throw IllegalArgumentException("edge $it has negative weight")
            }
        }

        edgeTo = MutableList(G.V()) { null }
        distTo = MutableList(G.V()) { 0.0 }
        pq = IndexMinPQ(G.V())
        for (v in 0 until G.V()) {
            distTo[v] = Double.POSITIVE_INFINITY
            distTo[s] = 0.0
            pq.insert(s, 0.0)

            while (!pq.isEmpty) {
                relax(G, pq.delMin())
            }
        }

        assert(check(G, s))
    }

    /**
     * 顶点的松弛
     *
     * @param G 加权有向图
     * @param v 指定的顶点
     */
    private fun relax(G: EdgeWeightedDigraph, v: Int) {
        for (e in G.adj(v)) {
            val w = e.to()
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight()
                edgeTo[w] = e
                if (pq.contains(w)) {
                    pq.change(w, distTo[w])
                } else {
                    pq.insert(w, distTo[w])
                }
            }
        }
    }

    /**
     * 到指定顶点的距离
     *
     * @param v 指定的顶点
     * @return 距离
     */
    fun distTo(v: Int): Double {
        validateVertex(v)
        return distTo[v]
    }

    /**
     * 检查到指定的顶点是否由路径
     *
     * @param v 指定的顶点
     * @return `true` 有路径
     *         `false` 没有路径
     */
    fun hasPathTo(v: Int): Boolean {
        validateVertex(v)
        return distTo[v] < Double.POSITIVE_INFINITY
    }

    /**
     * 到指定顶点的路径
     *
     * @param v 指定的顶点
     * @return 路径
     */
    fun pathTo(v: Int): Iterable<DirectedEdge>? {
        validateVertex(v)
        if (!hasPathTo(v)) return null

        val path = Stack<DirectedEdge>()
        var e = edgeTo[v]
        while (e != null) {
            path.push(e)
            e = edgeTo[e.from()]
        }
        return path
    }

    /**
     * 检查带权重的有向图
     *
     * @return 检查结果
     */
    private fun check(G: EdgeWeightedDigraph, s: Int): Boolean {
        G.edges().forEach {
            if (it.weight() < 0) {
                println("negative edge weight detected")
                return false
            }
        }

        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            println("distTo[s] and edgeTo[s] inconsistent")
            return false
        }
        for (v in 0 until G.V()) {
            if (v == s) continue
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                println("distTo[] and edgeTo[] inconsistent")
                return false
            }
        }

        for (v in 0 until G.V()) {
            G.adj(v).forEach {
                if (distTo[v] + it.weight() < distTo[it.to()]) {
                    println("edge $it not relaxed")
                    return false
                }
            }
        }

        for (w in 0 until G.V()) {
            if (edgeTo[w] == null) continue
            val e = edgeTo[w]
            val v = e!!.from()
            if (w != e.to()) {
                return false
            }
            if (distTo[v] + e.weight() != distTo[w]) {
                println("edge $e on shortest path not tight")
                return false
            }
        }
        return true
    }

    /**
     * 验证节点
     *
     * @param v 需要验证的节点
     */
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= distTo.size) {
            throw IllegalArgumentException("vertex $v is not between 0 and ${distTo - 1}")
        }
    }
}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = EdgeWeightedDigraph(input)
    val s = args[1].toInt()
    val sp = DijkstraSP(graph, s)

    for (v in 0 until graph.V()) {
        if (sp.hasPathTo(v)) {
            print("$s $v ${sp.distTo(v)}")
            sp.pathTo(v)!!.forEach {
                print("$it ")
            }
            println()
        } else {
            println("$s to $v   no path")
        }
    }
}