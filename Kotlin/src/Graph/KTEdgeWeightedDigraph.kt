package Graph

import edu.princeton.cs.algs4.Bag
import edu.princeton.cs.algs4.DirectedEdge
import edu.princeton.cs.algs4.In

/**
 * 加权有向图
 *
 * @author igaozp
 * @since 2017-09-10
 * @version 1.0
 */
class KTEdgeWeightedDigraph {
    /**
     * 顶点数量
     */
    private var V: Int = 0
    /**
     * 边的数量
     */
    private var E: Int = 0
    /**
     * 邻接表
     */
    private var adj: Array<Bag<DirectedEdge>>? = null

    /**
     * 构造方法
     *
     * @param V 顶点的数量
     */
    constructor(V: Int) {
        this.V = V
        this.E = 0
        adj = Array(V)
        for (v in 0 until V) {
            adj!![v] = Bag()
        }
    }

    /**
     * 构造方法
     *
     * @param input 输入流
     */
    constructor(input: In): this(input.readInt()) {
        val E = input.readInt()
        for (i in 0 until E) {
            val v = input.readInt()
            var w = input.readInt()
            adj!![v] = Bag()
        }
    }

    private fun <T> Array(size: Int): Array<T> = Array(size)

    /**
     * 获取顶点的数量
     *
     * @return 顶点的数量
     */
    fun V(): Int = V

    /**
     * 获取边的数量
     *
     * @return 边的数量
     */
    fun E(): Int = E

    /**
     * 向有向图中添加一条边
     *
     * @param e 带权重的边
     */
    fun addEdge(e: DirectedEdge) {
        adj!![e.from()].add(e)
        E++
    }

    /**
     * 获取以指定顶点的边
     *
     * @param v 指定的顶点
     * @return 边的集合
     */
    fun adj(v: Int): Iterable<DirectedEdge> = adj!![v]

    /**
     * 获取有向图所有的边
     *
     * @return 边的集合
     */
    fun edges(): Iterable<DirectedEdge> {
        val bag = Bag<DirectedEdge>()
        (0 until V).forEach { v ->
            adj!![v].forEach { e -> bag.add(e) }
        }
        return bag
    }
}