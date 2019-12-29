package io.metatom.graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.Edge
import edu.princeton.cs.algs4.EdgeWeightedGraph

/**
 * 最小生成树的 Kruskal 算法
 *
 * @author igaozp
 * @since 2017-09-10
 * @version 1.0
 */
class KruskallMST {
    // 最小生成树
    private var mst = Queue<Edge>()
    // 最小生成树的权重
    private var weight = 0.0

    /**
     * 构造方法
     *
     * @param G 加权无向图
     */
    constructor(G: EdgeWeightedGraph) {
        val pq = MinPQ<Edge>()
        for (e in G.edges()) {
            pq.insert(e)
        }
        val uf = UF(G.V())

        while (!pq.isEmpty && mst.size() < G.V() - 1) {
            val e = pq.delMin()
            val v = e.either()
            val w = e.other(v)
            if (uf.connected(v, w)) {
                continue
            }
            uf.union(v, w)
            mst.enqueue(e)
            weight += e.weight()
        }

        assert(check(G))
    }

    /**
     * 获取最小生成树
     *
     * @return 最小生成树
     */
    fun edges(): Iterable<Edge> = mst

    /**
     * 获取最小生成树的权重
     *
     * @return 最下生成树的权重
     */
    fun weight(): Double = weight

    /**
     * 检查带权重的无向图
     *
     * @param G 无向图
     * @return 检查结果
     */
    private fun check(G: EdgeWeightedGraph): Boolean {
        // check total weight
        var total = 0.0
        for (e in edges()) {
            total += e.weight()
        }
        if (Math.abs(total - weight()) > 1E-12) {
            println("Weighted of edges does not equals weight(): $total vs. ${weight()}")
            return false
        }

        // check that it is acyclic
        val uf = UF(G.V())
        for (e in edges()) {
            val v = e.either()
            val w = e.other(v)
            if (uf.connected(v, w)) {
                println("Not a forest")
                return false
            }
            uf.union(v, w)
        }

        // check that it is a spanning forest
        for (e in G.edges()) {
            val v = e.either()
            val w = e.other(v)
            if (!uf.connected(v, w)) {
                println("Not a spanning forest")
                return false
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (e in edges()) {
            // all edges in MST except e
            val uf = UF(G.V())
            for (f in mst) {
                val x = f.either()
                val y = f.other(x)
                if (f != e) {
                    uf.union(x, y)
                }
            }

            // check that e is min weight edge in crossing cut
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
    val mst = KruskallMST(graph)
    for (e in mst.edges()) {
        println(e)
    }
    println("${mst.weight()}")
}