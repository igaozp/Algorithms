package Graph

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;

/**
 * 无环加权有向图的最短路径算法
 *
 * @author igaozp
 * @since 2017-09-06
 * @version 1.0
 */
class KTAcyclicSP {
    /**
     * 路径的边
     */
    private var edgeTo: MutableList<DirectedEdge?>? = null
    /**
     * 路径长度
     */
    private var distTo: MutableList<Double?>? = null

    /**
     * 构造方法
     *
     * @param G 加权有向图
     * @param s 起始顶点
     */
    constructor(G: EdgeWeightedDigraph, s: Int) {
        this.edgeTo = MutableList(G.V(), { null })
        this.distTo = MutableList(G.V(), { null })
        for (v in 0 until G.V()) {
            distTo!![v] = Double.POSITIVE_INFINITY
        }
        distTo!![s] = 0.0

        val top = Topological(G)
        for (v in top.order()) {
            relax(G, v)
        }
    }

    /**
     * 初始化函数用的辅助函数
     */
    private fun Array(size: Int): Array<Any> = Array(size)

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
            }
        }
    }

    /**
     * 到达指定顶点的路径长度
     *
     * @param v 指定的顶点
     * @return 路径长度
     */
    fun distTo(v: Int): Double = distTo?.get(v)!!

    /**
     * 检查到指定顶点是否有路径
     *
     * @param v 指定的顶点
     * @return `true` 有路径
     *         `false` 没有路径
     */
    fun hasPathTo(v: Int): Boolean = distTo?.get(v)!! < Double.POSITIVE_INFINITY

    /**
     * 获取到指定顶点的路径
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