package me.metatom.kotlin.graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.Edge
import edu.princeton.cs.algs4.EdgeWeightedGraph
import edu.princeton.cs.algs4.LazyPrimMST

/**
 * 最小生成树的 Prim 算法的延时实现
 *
 * @author igaozp
 * @since 2017-09-10
 * @version 1.0
 */
class LazyPrimMST {
    // 最小生成树的顶点
    private var marked = MutableList(0) { false }
    // 最小生成树的边
    private var mst = Queue<Edge>()
    // 横切边
    private var pq = MinPQ<Edge>()
    // 生成树的权重
    private var weight = 0.0

    /**
     * 构造方法
     *
     * @param G 加权无向图
     */
    constructor(G: EdgeWeightedGraph) {
        marked = MutableList(G.V()) { false }

        for (v in 0 until G.V()) {
            if (!marked[v]) prim(G, v)
        }

        assert(check(G))
    }

    /**
     * 访问加权无向图的顶点
     *
     * @param G 加权无向图
     * @param s 指定的顶点
     */
    private fun prim(G: EdgeWeightedGraph, s: Int) {
        scan(G, s)
        while (!pq.isEmpty) {
            val e = pq.delMin()
            val v = e.either()
            val w = e.other(v)
            assert(marked[v] || marked[w])
            if (marked[v] && marked[w]) {
                continue
            }
            mst.enqueue(e)
            weight += e.weight()
            if (!marked[v]) scan(G, v)
            if (!marked[w]) scan(G, w)
        }
    }

    /**
     * 访问加权无向图的顶点
     *
     * @param G 加权无向图
     * @param v 指定的顶点
     */
    private fun scan(G: EdgeWeightedGraph, v: Int) {
        assert(!marked[v])
        marked[v] = true
        for (e in G.adj(v)) {
            if (!marked[e.other(v)]) {
                pq.insert(e)
            }
        }
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
     * @return 最小生成树的权重
     */
    fun weight(): Double = weight

    /**
     * 检查加权无向图
     *
     * @param G 无向图
     * @return 检查结果
     */
    private fun check(G: EdgeWeightedGraph): Boolean {
        // check weight
        var totalWeight = 0.0
        for (e in edges()) {
            totalWeight += e.weight()
        }
        if (Math.abs(totalWeight - weight()) > 1E-12) {
            println("Weight of edges does not equal weight(): $totalWeight vs. ${weight()}")
            return false
        }

        // check that it is acyclic
        var uf = UF(G.V())
        for (e in edges()) {
            val v = e.either()
            val w = e.other(v)
            if (uf.connected(v, w)) {
                System.err.println("Not a forest")
                return false
            }
            uf.union(v, w)
        }

        // check that it is a spanning forest
        for (e in G.edges()) {
            val v = e.either()
            val w = e.other(v)
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest")
                return false
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (e in edges()) {
            // all edges in MST except e
            uf = UF(G.V())
            for (f in mst) {
                val x = f.either()
                val y = f.other(x)
                if (f !== e) uf.union(x, y)
            }

            // check that e is min weight edge in crossing cut
            for (f in G.edges()) {
                val x = f.either()
                val y = f.other(x)
                if (!uf.connected(x, y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge $f violates cut optimality conditions")
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
    val mst = LazyPrimMST(graph)
    for (e in mst.edges()) {
        println(e)
    }
    println("${mst.weight()}")
}