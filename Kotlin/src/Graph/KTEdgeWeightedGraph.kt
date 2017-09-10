package Graph

import edu.princeton.cs.algs4.Bag
import edu.princeton.cs.algs4.Edge
import edu.princeton.cs.algs4.In

/**
 * 加权无向图
 *
 * @author igaozp
 * @since 2017-09-10
 * @version 1.0
 */
class KTEdgeWeightedGraph {
    /**
     * 顶点的数量
     */
    private var V: Int = 0
    /**
     * 边的数量
     */
    private var E: Int = 0
    /**
     * 邻接表
     */
    private var adj: Array<Bag<Edge>>? = null

    /**
     * 构造方法
     *
     * @param V 初始化的顶点数量
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
            val w = input.readInt()
            adj!![v] = Bag()
        }
    }

    /**
     * 生成函数的辅助方法
     */
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
     * 添加一条边
     *
     * @param e 新增的边
     */
    fun addEdge(e: Edge) {
        val v = e.either()
        val w = e.other(v)

        adj!![v].add(e)
        adj!![w].add(e)
        E++
    }

    /**
     * 获取指定以顶点为开始的边
     *
     * @param v 指定的顶点
     * @return 边的集合
     */
    fun adj(v: Int): Iterable<Edge> = adj!![v]

    /**
     * 获取图中的所有边
     *
     * @return 边的集合
     */
    fun edges(): Iterable<Edge> {
        val b = Bag<Edge>()
        for (v in 0 until V) {
            adj!![v].forEach { e ->
                if (e.other(v) > V) {
                    b.add(e)
                }
            }
        }
        return b
    }
}
