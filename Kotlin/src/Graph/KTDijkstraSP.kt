package Graph

import edu.princeton.cs.algs4.DirectedEdge
import edu.princeton.cs.algs4.EdgeWeightedDigraph
import edu.princeton.cs.algs4.IndexMinPQ
import java.util.*

/**
 * 最短路径的 Dijkstra 算法
 *
 * @author igaozp
 * @since 2017-09-09
 * @version 1.0
 */
class KTDijkstraSP(G: EdgeWeightedDigraph, s: Int) {
    /**
     * 距离路径最近的边
     */
    private var edgeTo: MutableList<DirectedEdge?>? = null
    /**
     * 边的权重
     */
    private var distTo: MutableList<Double?>? = null
    /**
     * 有效的横切边
     */
    private var pq: IndexMinPQ<Double>? = null

    /**
     * 构造方法
     */
    init {
        edgeTo = MutableList(G.V(), { null })
        distTo = MutableList(G.V(), { null })
        pq = IndexMinPQ(G.V())
        for (v in 0 until G.V()) {
            distTo!![v] = Double.POSITIVE_INFINITY
            distTo!![s] = 0.0
            pq!!.insert(s, 0.0)

            while (!pq!!.isEmpty) {
                relax(G, pq!!.delMin())
            }
        }
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
            if (distTo?.get(w)!! > distTo?.get(v)!! + e.weight()) {
                distTo!![w] = distTo?.get(v)!! + e.weight()
                edgeTo!![w] = e
                if (pq!!.contains(w)) {
                    pq!!.change(w, distTo!![w])
                } else {
                    pq!!.insert(w, distTo!![w])
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
    fun distTo(v: Int): Double = distTo?.get(v)!!

    /**
     * 检查到指定的顶点是否由路径
     *
     * @param v 指定的顶点
     * @return `true` 有路径
     *         `false` 没有路径
     */
    fun hasPathTo(v: Int): Boolean = distTo?.get(v)!! < Double.POSITIVE_INFINITY

    /**
     * 到指定顶点的路径
     *
     * @param v 指定的顶点
     * @return 路径
     */
    fun pathTo(v: Int): Iterable<DirectedEdge>? {
        if (!hasPathTo(v)) return null

        val path = Stack<DirectedEdge>()
        var e = edgeTo?.get(v)
        while (e != null) {
            path.push(e)
            e = edgeTo!![e.from()]
        }
        return path
    }
}