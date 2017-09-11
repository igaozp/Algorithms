package Graph

import edu.princeton.cs.algs4.Bag
import edu.princeton.cs.algs4.Edge
import edu.princeton.cs.algs4.EdgeWeightedGraph
import edu.princeton.cs.algs4.IndexMinPQ

/**
 * 最小生成树的 Prim 算法
 *
 * @author igaozp
 * @since 2017-09-11
 * @version 1.0
 */
class KTPrimMST(G: EdgeWeightedGraph) {
    /**
     * 距离树最近的边
     */
    private var edgeTo: Array<Edge>? = null
    /**
     * 存储边的权重
     */
    private var distTo: Array<Double>? = null
    /**
     * 顶点的访问情况
     */
    private var marked: Array<Boolean>? = null
    /**
     * 有效的横切边
     */
    private var pq: IndexMinPQ<Double>? = null

    /**
     * 构造方法
     *
     * @param G 加权无向图
     */
    init {
        edgeTo = Array(G.V())
        distTo = Array(G.V())
        marked = Array(G.V())
        for (v in 0 until G.V()) {
            distTo!![v] = Double.POSITIVE_INFINITY
        }
        pq = IndexMinPQ(G.V())

        distTo!![0] = 0.0
        pq!!.insert(0, 0.0)
        while (!pq!!.isEmpty) {
            visit(G, pq!!.delMin())
        }
    }

    /**
     * 初始化数组的辅助方法
     */
    private fun <T> Array(size: Int): Array<T> = Array(size)

    /**
     * 访问加权无向图的顶点
     *
     * @param G 加权无向图
     * @param v 指定的顶点
     */
    private fun visit(G: EdgeWeightedGraph, v: Int) {
        marked!![v] = true
        for (e in G.adj(v)) {
            val w = e.other(v)

            if (marked!![w]) {
                continue
            }
            if (e.weight() < distTo!![w]) {
                edgeTo!![w] = e
                distTo!![w] = e.weight()

                if (pq!!.contains(w)) {
                    pq!!.change(w, distTo!![w])
                } else {
                    pq!!.insert(w, distTo!![w])
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
        (1 until edgeTo!!.size).forEach { v -> mst.add(edgeTo!![v]) }
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
}