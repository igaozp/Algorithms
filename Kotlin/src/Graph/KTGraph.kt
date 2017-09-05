package Graph

import edu.princeton.cs.algs4.Bag
import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.Graph

/**
 * 无向图
 *
 * 图由一组顶点和一组能够将两个顶点相连的边组成的
 * 使用邻接表实现无向图：一个以顶点为索引的列表数组，
 * 其中的每一个元素和该顶点相邻的顶点列表
 *
 * @author igaozp
 * @since 2017-09-05
 * @version 1.0
 */
class KTGraph {
    /**
     * 无向图的顶点数量
     */
    private val V: Int
    /**
     * 无向图的边的数量
     */
    private var E: Int = 0
    /**
     * 邻接表
     */
    private var adj: Array<Bag<Int>>? = null

    /**
     * 构造函数
     *
     * @param V 初始化的顶点数量
     */
    constructor(V: Int) {
        this.V = V
        this.E = E
        adj = Array(V)
        for (v in 0 until V) {
            adj!![v] = Bag()
        }
    }

    /**
     * 读取无向图并初始化
     *
     * @param input 数据输入流
     */
    constructor(input: In):this(input.readInt()) {
        val E = input.readInt()
        for (i in 0 until E) {
            val v = input.readInt()
            val w = input.readInt()
            adj!![v] = Bag()
        }
    }

    /**
     * 初始化数组的辅助函数
     */
    private fun Array(size: Int): Array<Bag<Int>> = Array(size)

    /**
     * 获取无向图的顶点数量
     *
     * @return 顶点数量
     */
    fun V(): Int = V

    /**
     * 获取无向图边的数量
     *
     * @return 边的数量
     */
    fun E(): Int = E

    /**
     * 向无向图中增加一条边
     *
     * @param v 边的起始顶点
     * @param w 边的结束顶点
     */
    fun addEdge(v: Int, w: Int) {
        adj!![v].add(w)
        adj!![w].add(v)
        E++
    }

    /**
     * 获取与指定顶点相连的顶点集合
     *
     * @param v 指定的顶点
     * @return 包含相连顶点的集合
     */
    fun adj(v: Int): Iterable<Int> = adj!![v]

    /**
     * 在指定的图中获取某个顶点的度数
     *
     * @param G 指定的图
     * @param v 指定的顶点
     * @return 该顶点的度数
     */
    fun degree(G: Graph, v: Int): Int = G.adj(v).count()

    /**
     * 获取图中最大的度数
     *
     * @param G 指定的图
     * @return 最大的度数
     */
    fun maxDegree(G: Graph): Int {
        var max = 0
        (0 until G.V())
                .asSequence()
                .filter { degree(G, it) > max }
                .forEach { max = degree(G, it) }
        return max
    }

    /**
     * 获取图的平均度数
     *
     * @param G 指定的图
     * @return 平均度数
     */
    fun avgDegree(G: Graph): Double = 2.0 * G.E() / G.V()

    /**
     * 获取图的自环数量
     *
     * @param G 指定的图
     * @return 自环的数量
     */
    fun numberOfSelfLoops(G: Graph): Int {
        var count = 0
        for (v in 0 until G.V()) {
            G.adj(v)
                    .filter { v == it }
                    .forEach { count++ }
        }
        return count / 2
    }

    override fun toString(): String {
        val s = StringBuilder("$V vertices, $E edges\n")
        for (v in 0 until V) {
            s.append(v).append(": ")
            for (w in this.adj(v)) {
                s.append(w).append(" ")
            }
            s.append("\n")
        }
        return s.toString()
    }
}