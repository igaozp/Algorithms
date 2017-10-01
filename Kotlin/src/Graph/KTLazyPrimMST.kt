package Graph

import edu.princeton.cs.algs4.EdgeWeightedGraph
import edu.princeton.cs.algs4.Edge
import edu.princeton.cs.algs4.MinPQ
import edu.princeton.cs.algs4.Queue

/**
 * 最小生成树的 Prim 算法的延时实现
 *
 * @author igaozp
 * @since 2017-09-10
 * @version 1.0
 */
class KTLazyPrimMST {
    /**
     * 最小生成树的顶点
     */
    private var marked: MutableList<Boolean?>? = null
    /**
     * 最小生成树的边
     */
    private var mst: Queue<Edge>? = null
    /**
     * 横切边
     */
    private var pq: MinPQ<Edge>? = null

    /**
     * 构造方法
     *
     * @param G 加权无向图
     */
    constructor(G: EdgeWeightedGraph) {
        pq = MinPQ()
        marked = MutableList(G.V(), { null })
        mst = Queue()

        visit(G, 0)

        while (!pq!!.isEmpty) {
            val e = pq!!.delMin()
            val v = e.either()
            val w = e.other(v)

            if (marked?.get(v)!! && marked?.get(w)!!) {
                continue
            }

            mst!!.enqueue(e)

            if (!marked?.get(v)!!) {
                visit(G, v)
            }
            if (!marked?.get(w)!!) {
                visit(G, w)
            }
        }
    }

    /**
     * 访问加权无向图的顶点
     *
     * @param G 加权无向图
     * @param v 指定的顶点
     */
    private fun visit(G: EdgeWeightedGraph, v: Int) {
        marked!![v] = true
        G.adj(v)
                .filterNot { marked?.get(it.other(v))!! }
                .forEach { pq!!.insert(it) }
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
     * @return 最小生成树的权重
     */
    fun weight(): Double {
        var weight = 0.0
        this.edges().forEach { e -> weight += e.weight() }
        return weight
    }
}