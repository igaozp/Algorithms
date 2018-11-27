package me.metatom.kotlin.graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.DirectedEdge
import edu.princeton.cs.algs4.EdgeWeightedDigraph

/**
 * 基于队列的 Bellman-Ford 算法
 *
 * @author igaozp
 * @since 2017-09-07
 * @version 1.0
 */
class BellmanFordSP(G: EdgeWeightedDigraph, s: Int) {
    // 到某个顶点的路径长度
    private var distTo: MutableList<Double?> = MutableList(0) { null }
    // 到某个顶点的最后一条边
    private var edgeTo: MutableList<DirectedEdge?> = MutableList(0) { null }
    // 该顶点是否在队列中
    private var onQ: MutableList<Boolean?> = MutableList(0) { null }
    // 正在被放松的顶点
    private var queue: Queue<Int> = Queue()
    // relax() 的调用次数
    private var cost: Int = 0
    // 是否有负权重环
    private var cycle: Iterable<DirectedEdge>? = null

    // 构造函数
    init {
        distTo = MutableList(G.V()) { null }
        edgeTo = MutableList(G.V()) { null }
        onQ = MutableList(G.V()) { null }
        queue = Queue()
        for (v in 0 until G.V()) {
            distTo[v] = Double.POSITIVE_INFINITY
        }
        distTo[s] = 0.0
        queue.enqueue(s)
        onQ[s] = true
        while (!queue.isEmpty && !hasNegativeCycle()) {
            val v = queue.dequeue()
            onQ[v] = false
            relax(G, v)
        }

        assert(check(G, s))
    }

    // 顶点的松弛
    private fun relax(G: EdgeWeightedDigraph, v: Int) {
        for (e in G.adj(v)) {
            val w = e.to()
            if (distTo[w]!! > distTo[v]!! + e.weight()) {
                distTo[w] = distTo[v]!! + e.weight()
                edgeTo[w] = e
                if (!onQ[w]!!) {
                    queue.enqueue(w)
                    onQ[w] = true
                }
            }
            if (cost++ % G.V() == 0) {
                findNegativeCycle()
                if (hasNegativeCycle()) return
            }
        }
    }

    // 到指定顶点的路径长度
    fun distTo(v: Int): Double {
        validateVertex(v)
        if (hasNegativeCycle()) throw UnsupportedOperationException("Negative cost cycle exists")
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
        if (hasNegativeCycle()) throw UnsupportedOperationException("Negative cost cycle exists")
        if (!hasPathTo(v)) return null
        val path = Stack<DirectedEdge>()
        var e = edgeTo[v]
        while (e != null) {
            path.push(e)
            e = edgeTo[e.from()]
        }
        return path
    }

    // 查找负权重的环
    private fun findNegativeCycle() {
        val V = edgeTo.size
        val spt = EdgeWeightedDigraph(V)
        (0 until V)
                .filter { edgeTo[it] != null }
                .forEach { spt.addEdge(edgeTo[it]) }
        val cf = EdgeWeightedDirectedCycle(spt)
        cycle = cf.cycle()
    }

    // 检查是否有负权重的环
    private fun hasNegativeCycle(): Boolean = cycle != null

    // 获取负权重的环
    private fun negativeCycle(): Iterable<DirectedEdge> = cycle!!

    // 验证顶点
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= distTo.size) throw IllegalArgumentException("vertex  $v is not between 0 and ${distTo.size - 1}")
    }

    // 检查有向图
    private fun check(G: EdgeWeightedDigraph, s: Int): Boolean {
        if (hasNegativeCycle()) {
            var weight = 0.0
            for (e in negativeCycle()) {
                weight += e.weight()
            }
            if (weight >= 0.0) {
                error("error: weight of negative cycle = $weight")
            }
        } else {
            if (distTo[s] != 0.0 || edgeTo[s] != null) {
                error("distanceTo[s] and edgeTo[s] inconsistent")
            }
            for (v in 0 until G.V()) {
                if (v == s) continue
                if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                    error("distTo[] and edgeTo[] inconsistent")
                }
            }

            for (v in 0 until G.V()) {
                for (e in G.adj(v)) {
                    val w = e.to()
                    if (distTo[v]!! + e.weight() < distTo[w]!!) {
                        error("edge $s not relaxed")
                    }
                }
            }

            for (w in 0 until G.V()) {
                if (edgeTo[w] == null) continue
                val e = edgeTo[w]
                val v = e!!.from()
                if (w != e.to()) return false
                if (distTo[v]!! + e.weight() != distTo[w]) {
                    error("edge $e on shortest path not tight")
                }
            }
        }

        println("Satisfies optimality conditions")
        return true
    }
}