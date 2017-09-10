package Graph

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
class KTKruskallMST {
    /**
     * 最小生成树
     */
    private var mst: Queue<Edge>? = null

    /**
     * 构造方法
     *
     * @param G 加权无向图
     */
    constructor(G: EdgeWeightedGraph) {
        mst = Queue()
        val pq = MinPQ<Edge>()
        for (e in G.edges()) {
            pq.insert(e)
        }
        val uf = UF(G.V())

        while (!pq.isEmpty && mst!!.size() < G.V() - 1) {
            val e = pq.delMin()
            val v = e.either()
            val w = e.other(v)
            if (uf.connected(v, w)) {
                continue
            }
            uf.union(v, w)
            mst!!.enqueue(e)
        }
    }

    /**
     * 获取最小生成树
     *
     * @return 最小生成树
     */
    fun edges(): Iterable<Edge> = mst!!

    /**
     * 获取最小生成树的权重
     *
     * @return 最下生成树的权重
     */
    fun weight(): Double {
        var weight = 0.0
        this.edges().forEach { e -> weight += e.weight() }
        return weight
    }
}