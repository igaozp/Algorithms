package Graph

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
class KTBellmanFordSP(G: EdgeWeightedDigraph, s: Int) {
    /**
     * 到某个顶点的路径长度
     */
    private  var distTo: MutableList<Double?>? = null
    /**
     * 到某个顶点的最后一条边
     */
    private var edgeTo: MutableList<DirectedEdge?>? = null
    /**
     * 该顶点是否在队列中
     */
    private var onQ: MutableList<Boolean?>? = null
    /**
     * 正在被放松的顶点
     */
    private var queue: Queue<Int>? = null
    /**
     * relax() 的调用次数
     */
    private var cost: Int = 0
    /**
     * 是否有负权重环
     */
    private var cycle: Iterable<DirectedEdge>? = null

    /**
     * 构造函数
     */
    init {
        distTo = MutableList(G.V(), { null })
        edgeTo = MutableList(G.V(), { null })
        onQ = MutableList(G.V(), { null })
        queue = Queue()
        for (v in 0 until G.V()) {
            distTo!![v] = Double.POSITIVE_INFINITY
        }
        distTo!![s] = 0.0
        queue!!.enqueue(s)
        onQ!![s] = true
        while (!queue!!.isEmpty && !hasNegativeCycle()) {
            val v = queue!!.dequeue()
            onQ!![v] = false
            relax(G, v)
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
                if (!onQ?.get(w)!!) {
                    queue!!.enqueue(w)
                    onQ!![w] = true
                }
            }
            if (cost++ % G.V() == 0) {
                findNegativeCycle()
            }
        }
    }

    /**
     * 到指定顶点的路径长度
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

    /**
     * 查找负权重的环
     */
    private fun findNegativeCycle() {
        val V = edgeTo!!.size
        val spt = EdgeWeightedDigraph(V)
        (0 until V)
                .filter { edgeTo!![it] != null }
                .forEach { spt.addEdge(edgeTo!![it]) }
        val cf = EdgeWeightedDirectedCycle(spt)
        cycle = cf.cycle()
    }

    /**
     * 检查是否有负权重的环
     *
     * @return `true` 有负权重的环
     *         `false` 没有负权重的环
     */
    fun hasNegativeCycle(): Boolean = cycle != null

    /**
     * 获取负权重的环
     *
     * @return 负权重的环
     */
    fun negativeCycle(): Iterable<DirectedEdge> = cycle!!
}