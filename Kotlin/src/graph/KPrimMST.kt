package graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.Edge
import edu.princeton.cs.algs4.EdgeWeightedGraph
import edu.princeton.cs.algs4.PrimMST

/**
 * 最小生成树的 Prim 算法
 *
 * @author igaozp
 * @since 2017-09-11
 * @version 1.0
 */
class KPrimMST(G: EdgeWeightedGraph) {
    // 距离树最近的边
    private var edgeTo = MutableList(0, { Edge(0, 0, 0.0) })
    // 存储边的权重
    private var distTo = MutableList(0, { 0.0 })
    // 顶点的访问情况
    private var marked = MutableList(0, { false })
    // 有效的横切边
    private var pq = IndexMinPQ<Double>(0)

    /**
     * 构造方法
     *
     * @param G 加权无向图
     */
    init {
        edgeTo = MutableList(G.V(), { Edge(0, 0, 0.0) })
        distTo = MutableList(G.V(), { 0.0 })
        marked = MutableList(G.V(), { false })
        for (v in 0 until G.V()) {
            distTo[v] = Double.POSITIVE_INFINITY
        }
        pq = IndexMinPQ(G.V())

        distTo[0] = 0.0
        pq.insert(0, 0.0)
        while (!pq.isEmpty) {
            visit(G, pq.delMin())
        }

        assert(check(G))
    }

    /**
     * 访问加权无向图的顶点
     *
     * @param G 加权无向图
     * @param v 指定的顶点
     */
    private fun visit(G: EdgeWeightedGraph, v: Int) {
        marked[v] = true
        for (e in G.adj(v)) {
            val w = e.other(v)

            if (marked[w]) {
                continue
            }
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e
                distTo[w] = e.weight()

                if (pq.contains(w)) {
                    pq.change(w, distTo[w])
                } else {
                    pq.insert(w, distTo[w])
                }
            }
        }
    }

    /**
     * 获取最小生成树
     *
     * @return 最小生成树
     */
    fun edges(): Iterable<Edge> {
        val mst = Bag<Edge>()
        (1 until edgeTo.size).forEach { v -> mst.add(edgeTo[v]) }
        return mst
    }

    /**
     * 获取最小生成树的权重
     *
     * @return 最小生成树的权重
     */
    fun weight(): Double {
        var weight = 0.0
        this.edges().forEach { e -> weight += e.weight() }
        return weight
    }

    /**
     * 检查无向图
     *
     * @param G 需要检查的图
     * @return `true` 成功
     *         `false` 失败
     */
    private fun check(G: EdgeWeightedGraph): Boolean {
        // 检查图的权重
        var totalWeight = 0.0
        for (e in edges()) {
            totalWeight += e.weight()
        }
        if (Math.abs(totalWeight - weight()) > 1E-12) {
            println("Weight of edges does not equal weight(): $totalWeight vs. ${weight()}\n")
            return false
        }

        // 检查是否是无环图
        var uf = UF(G.V())
        for (e in edges()) {
            val v = e.either()
            val w = e.other(v)
            if (uf.connected(v, w)) {
                println("Not a forest")
                return false
            }
        }

        // 检查是否是生成树
        for (e in G.edges()) {
            val v = e.either()
            val w = e.other(v)
            if (!uf.connected(v, w)) {
                println("Not a spanning forest")
                return false
            }
        }

        // 检查是否是最小生成树
        for (e in edges()) {
            uf = UF(G.V())
            for (f in edges()) {
                val x = f.either()
                val y = f.other(x)
                if (f !== e) uf.union(x, y)
            }

            for (f in G.edges()) {
                val x = f.either()
                val y = f.other(x)
                if (!uf.connected(x, y)) {
                    if (f.weight() < e.weight()) {
                        println("Edge $f violates cut optimality conditions")
                        return false
                    }
                }
            }
        }

        return true
    }

}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = EdgeWeightedGraph(input)
    val mst = PrimMST(graph)
    for (e in mst.edges()) {
        println(e)
    }
    println("mst.weight()")
}